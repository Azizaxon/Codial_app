package uz.aziza.codial_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.databinding.FragmentMentorsAddBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Mentors


class MentorsAddFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var binding: FragmentMentorsAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMentorsAddBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(requireActivity())
        binding.imageSave.setOnClickListener {
            val surname = binding.edtMentorsSurname.text.toString().trim()
            val name = binding.edtMentorsName.text.toString().trim()
            val patronymic = binding.edtMentorsPatronymic.text.toString().trim()
            if (surname.isNotEmpty() && name.isNotEmpty() && patronymic.isNotEmpty()) {
                myDbHelper.addMentors(Mentors(surname , name , patronymic , myDbHelper.getCoursesByID(MyObject.courses.id!!)) , requireActivity())
            }
            binding.edtMentorsSurname.text!!.clear()
            binding.edtMentorsName.text!!.clear()
            binding.edtMentorsPatronymic.text!!.clear()
        }
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}