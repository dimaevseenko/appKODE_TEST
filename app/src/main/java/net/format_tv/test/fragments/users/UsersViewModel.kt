package net.format_tv.test.fragments.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.format_tv.test.models.Users
import net.format_tv.test.network.Loader

class UsersViewModel: ViewModel() {

    private val usersLiveData = MutableLiveData<Users?>()

    fun getUsers(listener: LoadUserListener? = null): Users?{
        if(usersLiveData.value == null)
            Loader().apply {
                loadUsers {
                    users -> usersLiveData.value = users
                    listener?.onLoaded(usersLiveData.value!!)
                }
            }

        return usersLiveData.value
    }

    interface LoadUserListener{
        fun onLoaded(users: Users)
    }
}