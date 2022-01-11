package net.format_tv.test.fragments.users.categories

import net.format_tv.test.fragments.users.UsersFragment

class QAUsersFragment: UsersFragment() {

    override fun getCategory(): String {
        return "qa"
    }
}