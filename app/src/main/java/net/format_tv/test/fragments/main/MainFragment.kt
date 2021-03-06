package net.format_tv.test.fragments.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentMainBinding
import net.format_tv.test.fragments.users.UserFragment
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
        binding.editTextTextPersonName2.addTextChangedListener {
            search(it.toString())
        }
    }

    private fun search(str: String){
        if(str != "")
                binding.imageButton.setColorFilter(Color.parseColor("#050510"))
        else
                binding.imageButton.colorFilter = null

        (binding.mainViewPager.adapter as MainViewPagerAdapter).getFragment(binding.mainViewPager.currentItem)?.let {
            it.search(str)
        }
    }

    fun getSortType(): UsersFragment.SortType{
        return sortType
    }

    fun setSortType(sortType: UsersFragment.SortType){
        this.sortType = sortType

        if(sortType == UsersFragment.SortType.DATE)
            binding.imageButtonSort.setColorFilter(Color.parseColor("#6534FF"))
        else
            binding.imageButtonSort.colorFilter = null

        getCurrentViewPagerItemFragment()?.updateSort()
    }

    fun getCurrentViewPagerItemFragment(): UsersFragment?{
        return (binding.mainViewPager.adapter as MainViewPagerAdapter).getFragment(binding.mainViewPager.currentItem)
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

     fun onBackPressed(): Boolean{
         val f = parentFragmentManager.findFragmentByTag("UserFragment") as? UserFragment
         f?.let {
             return f.dismiss()
         }
        return false
     }

    private fun getMainViewPagerAdapter(): MainViewPagerAdapter{
        return MainViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
    }
}