package uz.aziza.codial_app.classes

import android.app.Activity
import androidx.navigation.NavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.adapters.CoursesAdapter
import uz.aziza.codial_app.databinding.FragmentCoursesBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Courses

class ShowCourses(
    var activity: Activity,
    var binding: FragmentCoursesBinding,
    var findNavController: NavController,
) {
    lateinit var arrayListCourses: ArrayList<Courses>
    lateinit var coursesAdapter: CoursesAdapter
    lateinit var myDbHelper: MyDbHelper

    fun showCourses() {
        loadData()
        coursesAdapter = CoursesAdapter(arrayListCourses, object : CoursesAdapter.RVClickCourses {
            override fun onClick(courses: Courses) {
                MyObject.courses = courses
                findNavController.navigate(MyObject.navigationID)
            }

        })
        binding.recyclerCourses.adapter = coursesAdapter
    }

    private fun loadData() {
        arrayListCourses = ArrayList()
        myDbHelper = MyDbHelper(activity)
        arrayListCourses = myDbHelper.showCourses()
    }
}