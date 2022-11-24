package uz.aziza.codial_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.databinding.DeleteDialogBinding
import uz.aziza.codial_app.databinding.FragmentCoursesShowBinding
import uz.aziza.codial_app.db.MyDbHelper

class CoursesShowFragment : Fragment() {
    private lateinit var binding: FragmentCoursesShowBinding
    lateinit var bindingDelete: DeleteDialogBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var dialogDelete: AlertDialog
    private var booleanAntiBag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCoursesShowBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(requireActivity())
        binding.tvAllCourseName.text = MyObject.courses.name
        binding.tvDescription.text = MyObject.courses.about
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageDelete.setOnClickListener {
            if (booleanAntiBag) {
                buildDeleteDialog()
                booleanAntiBag = false
            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun buildDeleteDialog() {
        val alertDialog = AlertDialog.Builder(requireActivity())
        bindingDelete = DeleteDialogBinding.inflate(requireActivity().layoutInflater)

        bindingDelete.tvDescription.text =
            "Bu kursni o'chirib tashlamoqchimisiz? , agar siz kursni o'chirsangiz, u bilan bog'langan mentorlar, guruhlar va talabalar ham o'chiriladi!"

        bindingDelete.tvCancel.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDbHelper.deleteCourses(MyObject.courses)
            if (boolean) {
                Toast.makeText(activity, "Muvaffaqiyatli o'chirildi!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "O'chirib bo'lmadi", Toast.LENGTH_SHORT).show()
            }
            dialogDelete.cancel()
            findNavController().popBackStack()
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }
        alertDialog.setView(bindingDelete.root)
        dialogDelete = alertDialog.create()
        dialogDelete.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialogDelete.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogDelete.show()

    }

}