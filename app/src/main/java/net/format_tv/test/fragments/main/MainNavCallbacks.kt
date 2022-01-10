package net.format_tv.test.fragments.main

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class MainNavCallbacks(private val tabLayout: TabLayout, private val viewPager2: ViewPager2): ViewPager2.OnPageChangeCallback(), TabLayout.OnTabSelectedListener {

    override fun onPageSelected(position: Int) {
        tabLayout.getTabAt(position)?.select()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            viewPager2.currentItem = it.position
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}