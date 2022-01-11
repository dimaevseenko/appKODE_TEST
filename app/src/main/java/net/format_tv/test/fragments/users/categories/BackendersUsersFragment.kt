package net.format_tv.test.fragments.users.categories

import net.format_tv.test.fragments.users.UsersFragment

class BackendersUsersFragment: UsersFragment() {

    override fun getCategory(): String {
        return "backend"
    }
}