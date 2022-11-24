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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.adapters.GroupsAdapter
import uz.aziza.codial_app.databinding.DeleteDialogBinding
import uz.aziza.codial_app.databinding.EditDialogGroupsBinding
import uz.aziza.codial_app.databinding.FragmentItem2Binding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Groups
import uz.aziza.codial_app.models.Mentors


class ItemFragment2 : Fragment() {
    private lateinit var dialog: AlertDialog
    lateinit var myDbHelper: MyDbHelper
    lateinit var binding: FragmentItem2Binding
    lateinit var bindingDialog: EditDialogGroupsBinding
    lateinit var groupsAdapter: GroupsAdapter
    lateinit var bindingDelete: DeleteDialogBinding
    lateinit var dialogDelete: AlertDialog
    lateinit var arrayListMentors: ArrayList<Mentors>
    lateinit var arrayListMentorsString: ArrayList<String>
    lateinit var arrayListTime: ArrayList<String>
    lateinit var arrayListDay: ArrayList<String>
    lateinit var arrayAdapterTimes: ArrayAdapter<String>
    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterMentors: ArrayAdapter<String>
    var booleanAntiBag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItem2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
        binding.recyclerViewGroups2.adapter = groupsAdapter
    }

    private fun loadData() {
        myDbHelper = MyDbHelper(requireActivity())
        var arrayListGroups = myDbHelper.showGroups("1", requireActivity())
        val arrayList = ArrayList<Groups>()
        for (i in arrayListGroups) {
            if (i.courses!!.id ==
                MyObject.courses.id) {
                arrayList.add(i)
            }
        }
        arrayListGroups = arrayList
        groupsAdapter = GroupsAdapter(requireActivity(),
            arrayListGroups,
            object : GroupsAdapter.RVClickGroups {
                override fun showGroups(groups: Groups) {

                    MyObject.groups = groups
                    findNavController().navigate(R.id.action_groupsFragment_to_groupsShowFragment)
                }

                override fun editGroups(groups: Groups, position: Int) {
                    if (booleanAntiBag) {
                        loadDataDialog(groups)
                        buildDialog(groups)
                        booleanAntiBag = false
                    }
                }

                override fun deleteGroups(groups: Groups) {
                    if (booleanAntiBag){
                        buildDialogDelete(groups)
                        booleanAntiBag = false
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun buildDialog(groups: Groups) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingDialog.edtGroupsName.setText(groups.name)
        bindingDialog.spinnerMentors.setText("${groups.mentors!!.name} ${groups.mentors!!.surname}")
        bindingDialog.spinnerTimes.setText(groups.times)
        bindingDialog.spinnerDays.setText(groups.days)
        bindingDialog.spinnerTimes.setAdapter(arrayAdapterTimes)
        bindingDialog.spinnerDays.setAdapter(arrayAdapterDays)
        bindingDialog.spinnerMentors.setAdapter(arrayAdapterMentors)


        bindingDialog.spinnerMentors.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {

                    MyObject.mentors = arrayListMentors[position]
                }
            }

        bindingDialog.tvCancel.setOnClickListener {
            dialog.cancel()
        }

        bindingDialog.tvSave.setOnClickListener {
            if (bindingDialog.edtGroupsName.text.toString().trim()
                    .isNotEmpty() && bindingDialog.spinnerMentors.text.toString().trim()
                    .isNotEmpty() && bindingDialog.spinnerTimes.text.toString().trim().isNotEmpty()
                && bindingDialog.spinnerDays.text.toString().trim().isNotEmpty()
            ) {
                val groupID = groups.id
                val groupName = bindingDialog.edtGroupsName.text.toString().trim()
                val groupMentor =
                    MyObject.mentors
                val groupTime = bindingDialog.spinnerTimes.text.toString().trim()
                val groupDay = bindingDialog.spinnerDays.text.toString().trim()
                val groupCourses =
                    MyObject.courses
                val open = groups.open
                val groupsAdd = Groups(
                    groupID, groupName, groupMentor, groupTime, groupDay, groupCourses, open
                )

                myDbHelper.updateGroups(groupsAdd, requireActivity())
                onResume()

                dialog.cancel()
            }
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }

        alertDialog.setView(bindingDialog.root)
        dialog = alertDialog.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun loadDataDialog(groups: Groups) {
        bindingDialog = EditDialogGroupsBinding.inflate(layoutInflater)
        arrayListMentors = ArrayList()
        arrayListMentorsString = ArrayList()
        arrayListTime = ArrayList()
        arrayListDay = ArrayList()

            MyObject.mentors = groups.mentors!!

        arrayListTime.add("10:00 - 12:00")
        arrayListTime.add("12:00 - 14:00")
        arrayListTime.add("14:00 - 16:00")
        arrayListTime.add("16:00 - 18:00")
        arrayListTime.add("18:00 - 20:00")
        arrayListDay.add("Duyshanba-Chorshanba-Juma")
        arrayListDay.add("Seshanba-Payshanba-Shanba")

        arrayListMentors = myDbHelper.getAllMentorsByID(
            MyObject.courses.id!!)
        for (i in 0 until arrayListMentors.size) {
            arrayListMentorsString.add("${arrayListMentors[i].name!!} ${arrayListMentors[i].surname!!}")
        }

        arrayAdapterTimes = ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListTime)
        arrayAdapterDays = ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListDay)
        arrayAdapterMentors =
            ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListMentorsString)
    }

    @SuppressLint("SetTextI18n")
    private fun buildDialogDelete(groups: Groups) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingDelete = DeleteDialogBinding.inflate(layoutInflater)
        bindingDelete.tvDescription.text = "Bu guruhni o'chirib tashlamoqchimisiz ? , agar siz guruhni o'chirsangiz, unga aloqador talabalar ham o'chiriladi!"

        bindingDelete.tvCancel.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDbHelper.deleteGroups(groups)
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