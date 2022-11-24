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
import uz.aziza.codial_app.adapters.StudentsAdapter
import uz.aziza.codial_app.databinding.DeleteDialogBinding
import uz.aziza.codial_app.databinding.FragmentGroupsShowBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Students


class GroupsShowFragment : Fragment() {

    private lateinit var bindingDelete: DeleteDialogBinding
    lateinit var dialogDelete: AlertDialog
    lateinit var binding: FragmentGroupsShowBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var arrayListStudents: ArrayList<Students>
    lateinit var adapterStudents: StudentsAdapter
    var booleanAntiBag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGroupsShowBinding.inflate(layoutInflater)

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardStart.setOnClickListener {
            MyObject.groups.open = true
            val boolean = myDbHelper.startLessonGroup(MyObject.groups, requireActivity())
            if (boolean) {
                binding.cardStart.visibility = View.GONE
            }
        }

        binding.lyAdd.setOnClickListener {
            MyObject.editStudent = false
            findNavController().navigate(R.id.action_groupsShowFragment_to_studentAddEditFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
        showActivity()
        binding.recyclerViewStudents.adapter = adapterStudents
    }

    private fun loadData() {
        myDbHelper = MyDbHelper(requireActivity())
        arrayListStudents = ArrayList()
        arrayListStudents = myDbHelper.showStudents(MyObject.groups.id!!, MyObject.groups.open!!)
        adapterStudents = StudentsAdapter(requireActivity(),
            arrayListStudents,
            object : StudentsAdapter.RVClickStudents {
                override fun editStudents(students: Students) {
                    MyObject.editStudent = true
                    MyObject.students = students
                    findNavController().navigate(R.id.action_groupsShowFragment_to_studentAddEditFragment)
                }

                override fun deleteStudents(students: Students) {
                    if (booleanAntiBag) {
                        buildDialogDelete(students)
                        booleanAntiBag = false
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun showActivity() {
        binding.tvGroupName.text = MyObject.groups.name
        binding.tvGroupName2.text = "Guruh nomi: ${MyObject.groups.name}"
        binding.tvTime.text = "Vaqti: ${MyObject.groups.times}"
        binding.tvDay.text = "Kunlari: ${MyObject.groups.days}"
        binding.tvMentor.text =
            "Mentor: ${MyObject.groups.mentors!!.name} ${MyObject.groups.mentors!!.surname}"
        binding.tvCountStudent.text = "O’quvchilar soni: ${arrayListStudents.size} ta"
        if (MyObject.groups.open!!) {
            binding.cardStart.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buildDialogDelete(students: Students) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingDelete = DeleteDialogBinding.inflate(layoutInflater)
        bindingDelete.tvDescription.text = "Bu talabani o'chirib yubormoqchimisiz ?"

        bindingDelete.tvCancel.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDbHelper.deleteStudents(students)
            if (boolean){
                Toast.makeText(requireActivity(), "Muvaffaqiyatli o'chirildi!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "O'chirib bo'lmadi", Toast.LENGTH_SHORT).show()
            }
            dialogDelete.cancel()
            onResume()
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
