package uz.aziza.codial_app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.aziza.codial_app.databinding.ItemGroupsBinding
import uz.aziza.codial_app.db.MyDbHelper
import uz.aziza.codial_app.models.Groups

class GroupsAdapter(
    val context: Context,
    private val arrayList: ArrayList<Groups>,
    val rvClickGroups: RVClickGroups,
) :
    RecyclerView.Adapter<GroupsAdapter.VH>() {
    lateinit var myDbHelper: MyDbHelper

    inner class VH(private var itemRV: ItemGroupsBinding) : RecyclerView.ViewHolder(itemRV.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(groups: Groups, position: Int) {
            myDbHelper = MyDbHelper(context)
            val arrayListStudent = myDbHelper.showStudents(groups.id!! , groups.open!!)
            itemRV.tvName.text = groups.name
            itemRV.tvCountStudent.text = "O'quvchilar soni : ${arrayListStudent.size} ta"
            itemRV.cardShow.setOnClickListener {
                rvClickGroups.showGroups(groups)
            }
            itemRV.cardEdit.setOnClickListener {
                rvClickGroups.editGroups(groups, position)
            }
            itemRV.cardDelete.setOnClickListener {
                rvClickGroups.deleteGroups(groups)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(arrayList[position], position)

    }

    override fun getItemCount(): Int = arrayList.size

    interface RVClickGroups {
        fun showGroups(groups: Groups) {

        }

        fun editGroups(groups: Groups, position: Int) {

        }

        fun deleteGroups(groups: Groups) {

        }
    }

}