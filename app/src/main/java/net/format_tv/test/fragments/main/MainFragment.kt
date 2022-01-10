package net.format_tv.test.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentMainBinding

class MainFragment: Fragment(){

    private lateinit var binding: FragmentMainBinding

    private var mainNavCallbacks: MainNavCallbacks? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tabLayout.addOnTabSelectedListener(getMainNavCallbacks())
        binding.mainViewPager.registerOnPageChangeCallback(getMainNavCallbacks())
        binding.mainViewPager.adapter = getMainViewPagerAdapter()
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