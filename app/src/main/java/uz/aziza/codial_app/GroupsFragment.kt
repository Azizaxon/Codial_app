package uz.aziza.codial_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import uz.aziza.codial_app.Object.MyObject
import uz.aziza.codial_app.adapters.ViewPagerAdapter
import uz.aziza.codial_app.databinding.FragmentGroupsBinding
import uz.aziza.codial_app.db.MyDbHelper


class GroupsFragment : Fragment() {
    lateinit var binding: FragmentGroupsBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var arrayListFragment = arrayListOf<Fragment>()
    private lateinit var arrayListTypes: ArrayList<String>
    lateinit var arrayListViewPager: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loadData()
        binding.viewPager.adapter = viewPagerAdapter
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.lyAdd.setOnClickListener {
            findNavController().navigate(R.id.action_groupsFragment_to_groupsAddFragment)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = arrayListTypes[position]
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.imageAdd.visibility = View.INVISIBLE
                        binding.lyAdd.visibility = View.INVISIBLE
                    }
                    1 -> {
                        binding.imageAdd.visibility = View.VISIBLE
                        binding.lyAdd.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewPagerAdapter.notifyItemChanged(binding.viewPager.currentItem)
        return binding.root
    }

    private fun loadData() {
        arrayListTypes = ArrayList()
        myDbHelper = MyDbHelper(requireActivity())
        binding = FragmentGroupsBinding.inflate(layoutInflater)
        binding.tvCoursesName.text = MyObject.courses.name
        arrayListFragment = arrayListOf(
            ItemFragment2(), ItemFragment1()
        )

        arrayListTypes.add("Ochilgan\nguruhlar")
        arrayListTypes.add("Ochilayotgan\nguruhlar")
        viewPagerAdapter =
            ViewPagerAdapter(arrayListFragment, requireActivity().supportFragmentManager, lifecycle)
    }

}
