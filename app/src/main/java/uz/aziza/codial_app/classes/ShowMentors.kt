package uz.aziza.codial_app.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.R
import uz.aziza.codial_app.adapters.MentorsAdapter
import uz.aziza.codial_app.databinding.DeleteDialogBinding
import uz.aziza.codial_app.databinding.EditDialogMentorsBinding
import uz.aziza.codial_app.databinding.FragmentMentorBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Mentors

class ShowMentors(var activity: Activity, var binding: FragmentMentorBinding) {
    lateinit var arrayListMentors: ArrayList<Mentors>
    lateinit var mentorsAdapter: MentorsAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var dialogEdit: AlertDialog
    lateinit var dialogDelete: AlertDialog
    lateinit var bindingEdit: EditDialogMentorsBinding
    lateinit var bindingDelete: DeleteDialogBinding
    private var booleanAntiBag = true
    fun showMentors() {
        loadData()
        binding.recyclerMentors.adapter = mentorsAdapter
    }

    private fun loadData() {
        val arrayList = ArrayList<Mentors>()
        arrayListMentors = ArrayList()
        myDbHelper = MyDbHelper(activity)
        arrayListMentors = myDbHelper.showMentors()
        for (i in arrayListMentors) {
            if (i.courses!!.id == MyObject.courses.id) {
                arrayList.add(i)
            }
        }
        arrayListMentors = arrayList
        mentorsAdapter =
            MentorsAdapter(activity, arrayListMentors, object : MentorsAdapter.RVClickMentors {
                override fun onClickDelete(mentors: Mentors) {
                    if (booleanAntiBag) {
                        buildDialogDelete(mentors)
                        booleanAntiBag = false
                    }
                }

                override fun onClickEdit(mentors: Mentors) {
                    if (booleanAntiBag) {
                        buildDialogEdit(mentors)
                        booleanAntiBag = false
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun buildDialogDelete(mentors: Mentors) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingDelete = DeleteDialogBinding.inflate(activity.layoutInflater)
        bindingDelete.tvDescription.text =
            "Do you want to delete this mentor? , if you delete a mentor, the group and students associated with it will be deleted!"

        bindingDelete.tvCancel.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDbHelper.deleteMentors(mentors)
            if (boolean) {
                Toast.makeText(activity, "Successfully Delete!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Failed to Delete", Toast.LENGTH_SHORT).show()
            }
            dialogDelete.cancel()
            showMentors()
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

    private fun buildDialogEdit(mentors: Mentors) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingEdit = EditDialogMentorsBinding.inflate(activity.layoutInflater)
        bindingEdit.edtMentorsSurname.setText(mentors.surname)
        bindingEdit.edtMentorsName.setText(mentors.name)
        bindingEdit.edtMentorsPatronymic.setText(mentors.patronymic)
        bindingEdit.tvCancel.setOnClickListener {
            dialogEdit.cancel()
        }

        bindingEdit.tvSave.setOnClickListener {
            val surname = bindingEdit.edtMentorsSurname.text.toString().trim()
            val name = bindingEdit.edtMentorsName.text.toString().trim()
            val patronymic = bindingEdit.edtMentorsPatronymic.text.toString().trim()
            if (surname.isNotEmpty() && name.isNotEmpty() && patronymic.isNotEmpty()) {
                myDbHelper.updateMentors(
                    Mentors(
                        mentors.id,
                        surname,
                        name,
                        patronymic,
                        MyObject.courses
                    ), activity
                )
                showMentors()
                bindingEdit.edtMentorsSurname.text!!.clear()
                bindingEdit.edtMentorsName.text!!.clear()
                bindingEdit.edtMentorsPatronymic.text!!.clear()
                dialogEdit.cancel()
            }
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }

        alertDialog.setView(bindingEdit.root)
        dialogEdit = alertDialog.create()
        dialogEdit.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialogEdit.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEdit.show()
    }
}