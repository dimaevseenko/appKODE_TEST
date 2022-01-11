package net.format_tv.test.fragments.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentMainBinding
import net.format_tv.test.fragments.users.UsersFragment
import net.format_tv.test.fragments.users.sheet.BottomSortSheet

class MainFragment: Fragment(){

    private lateinit var binding: FragmentMainBinding

    private var mainNavCallbacks: MainNavCallbacks? = null

    private var sortType: UsersFragment.SortType = UsersFragment.SortType.ALPHABET

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tabLayout.addOnTabSelectedListener(getMainNavCallbacks())
        binding.mainViewPager.registerOnPageChangeCallback(getMainNavCallbacks())
        binding.mainViewPager.adapter = getMainViewPagerAdapter()
        binding.imageButtonSort.setOnClickListener { sort() }
    }

    fun getSortType(): UsersFragment.SortType{
        return sortType
    }

    fun setSortType(sortType: UsersFragment.SortType){
        this.sortType = sortType
        binding.mainViewPager.adapter = getMainViewPagerAdapter()
    }

    private fun sort(){
        BottomSortSheet().show(childFragmentManager, "SORT")
    }

    private fun getMainNavCallbacks(): MainNavCallbacks{
        if(mainNavCallbacks == null)
            mainNavCallbacks = MainNavCallbacks(
                binding.tabLayout,
                binding.mainViewPager
            )
        return mainNavCallbacks!!
    }

    private fun getMainViewPagerAdapter(): MainViewPagerAdapter{
        return MainViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
    }
}