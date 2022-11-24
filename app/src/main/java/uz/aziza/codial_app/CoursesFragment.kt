package uz.aziza.codial_app

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.classes.AddCourses
import uz.aziza.codial_app.classes.ShowCourses
import uz.aziza.codial_app.databinding.FragmentCoursesBinding


class CoursesFragment : Fragment() {
    lateinit var addCourses: AddCourses
    private lateinit var showCourses: ShowCourses
    lateinit var binding: FragmentCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loadData()
        showWindow()
        setOnClick()
        return binding.root
    }

    private fun loadData() {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        addCourses = AddCourses(requireActivity() , binding, findNavController())
        showCourses = ShowCourses(requireActivity(), binding , findNavController())
    }

    private fun showWindow() {
        binding.tvAllCourses.text = MyObject.tvAllCoursesName
        if (MyObject.booleanAddCourses) {
            binding.imageAdd.visibility = View.VISIBLE
            binding.lyAdd.visibility = View.VISIBLE
        }
        requireActivity().window.statusBarColor = Color.parseColor("#c78800")
        showCourses.showCourses()
    }

    private fun setOnClick() {
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.lyAdd.setOnClickListener {
            addCourses.buildDialog()
        }
    }

}