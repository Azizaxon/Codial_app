package uz.aziza.codial_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.databinding.FragmentStudentAddEditBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Students
import java.time.LocalDate
import androidx.annotation.RequiresApi as RequiresApi1

class StudentAddEditFragment : Fragment() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var binding: FragmentStudentAddEditBinding

    @RequiresApi1(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loadData()
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageSave.setOnClickListener {
            if (MyObject.editStudent) {
                editStudents()
            } else {
                addStudents()
            }
        }

        return binding.root
    }

    @RequiresApi1(Build.VERSION_CODES.O)
    private fun addStudents() {
        val name = binding.edtStudentName.text.toString().trim()
        val surname = binding.edtStudentSurname.text.toString().trim()
        val number = binding.edtStudentNumber.text.toString().trim()
        val date = LocalDate.now().toString()
        val students = Students(name, surname, number, date, MyObject.groups)

        if (name.isNotEmpty() && surname.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty()) {
            myDbHelper.addStudents(students, requireActivity())
            binding.edtStudentName.text!!.clear()
            binding.edtStudentSurname.text!!.clear()
            binding.edtStudentNumber.text!!.clear()
        }
    }

    private fun editStudents() {
        val name = binding.edtStudentName.text.toString().trim()
        val surname = binding.edtStudentSurname.text.toString().trim()
        val number = binding.edtStudentNumber.text.toString().trim()
        val students =
            Students(MyObject.students.id, name, surname, number, MyObject.students.day, MyObject.groups)
        myDbHelper.updateStudents(students, requireActivity())
        findNavController().popBackStack()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        binding = FragmentStudentAddEditBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(requireActivity())
        if (MyObject.editStudent) {
            binding.tvAllCourses.text = "Talaba o'zgartirish"
            binding.imageSave.setImageResource(R.drawable.ic_baseline_edit_24)
            binding.edtStudentName.setText(MyObject.students.name)
            binding.edtStudentSurname.setText(MyObject.students.surname)
            binding.edtStudentNumber.setText(MyObject.students.number)
        }
    }

}