package uz.aziza.codial_app.classes

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.NavController
import uz.aziza.codial_app.R
import uz.aziza.codial_app.databinding.AddCoursesDialogBinding
import uz.aziza.codial_app.databinding.FragmentCoursesBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Courses

class AddCourses (
    var activity: Activity,
    var binding: FragmentCoursesBinding,
    var findNavController: NavController,
) {
    private lateinit var bindingDialog: AddCoursesDialogBinding
    lateinit var dialog: AlertDialog
    lateinit var myDbHelper: MyDbHelper
    lateinit var showCourses: ShowCourses
    var booleanAntiBag = true
    fun buildDialog() {
        bindingDialog = AddCoursesDialogBinding.inflate(activity.layoutInflater)
        val alertDialog = AlertDialog.Builder(activity)

        bindingDialog.tvCancel.setOnClickListener {
            dialog.cancel()
        }

        bindingDialog.tvSave.setOnClickListener {
            val name = bindingDialog.edtCoursesName.text.toString().trim()
            val about = bindingDialog.edtCoursesAbout.text.toString().trim()
            if (name.isNotEmpty() && about.isNotEmpty()) {
                myDbHelper = MyDbHelper(activity)
                if (myDbHelper.addCourses(Courses(name, about), activity)) {
                    showCourses = ShowCourses(activity, binding, findNavController)
                    showCourses.showCourses()
                    dialog.cancel()
                }
            }
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }

        alertDialog.setView(bindingDialog.root)
        dialog = alertDialog.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (booleanAntiBag) {
            dialog.show()
            booleanAntiBag = false
        }
    }
}