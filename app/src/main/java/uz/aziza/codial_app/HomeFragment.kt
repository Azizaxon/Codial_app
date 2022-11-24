package uz.aziza.codial_app

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        requireActivity().window.statusBarColor = Color.parseColor("#000014")
        setOnClick()
        return binding.root
    }

    private fun setOnClick() {
        binding.cardCourses.setOnClickListener {
            fragmentNavigation("Barcha kurslar ro’yxati",
                true,
                R.id.action_coursesFragment_to_coursesShowFragment)
        }
        binding.cardGroups.setOnClickListener {
            fragmentNavigation("Barcha kurslar",
                false,
                R.id.action_coursesFragment_to_groupsFragment)
        }
        binding.cardMentors.setOnClickListener {
            fragmentNavigation("Barcha kurslar",
                false,
                R.id.action_coursesFragment_to_mentorFragment)
        }

    }

    private fun fragmentNavigation(string: String, boolean: Boolean, navigationId: Int) {
        MyObject.tvAllCoursesName = string
        MyObject.booleanAddCourses = boolean
        MyObject.navigationID = navigationId
        findNavController().navigate(R.id.action_homeFragment_to_coursesFragment)
    }

}