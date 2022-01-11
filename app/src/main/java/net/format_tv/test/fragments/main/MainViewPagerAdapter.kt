package net.format_tv.test.fragments.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.format_tv.test.fragments.users.UsersFragment
import net.format_tv.test.fragments.users.categories.*

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return getFragment(position)
    }

    private fun getFragment(position: Int): UsersFragment{
        when(position){
            0 -> return UsersFragment()
            1 -> return DesignersUsersFragment()
            2 -> return AnalystsUsersFragment()
            3 -> return ManagersUsersFragment()
            4 -> return iOSUsersFragment()
            5 -> return AndroidUsersFragment()
            6 -> return QAUsersFragment()
            7 -> return BackOfficerUsersFragment()
            8 -> return FrontendersUsersFragment()
            9 -> return HRsUsersFragment()
            10 -> return PRsUsersFragment()
            11 -> return BackendersUsersFragment()
            12 -> return SupportsUsersFragment()
        }
        return UsersFragment()
    }

    override fun getItemCount(): Int {
        return 13
    }
}