package uz.aziza.codial_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.classes.ShowMentors
import uz.aziza.codial_app.databinding.FragmentMentorBinding


class MentorFragment : Fragment() {
    lateinit var binding: FragmentMentorBinding
    lateinit var showMentors: ShowMentors
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loadData()
        showWindow()
        setOnClick()

        return binding.root
    }

    private fun setOnClick() {
        binding.lyAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mentorFragment_to_mentorsAddFragment)
        }
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadData() {
        binding = FragmentMentorBinding.inflate(layoutInflater)
        showMentors = ShowMentors(requireActivity(), binding)
    }

    private fun showWindow() {
        binding.tvCoursesName.text = MyObject.courses.name
        showMentors.showMentors()
    }
}