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
                    if(usersLiveData.value == null)
                        listener?.onFailure()
                    else
                        listener?.onLoaded(usersLiveData.value!!)
                }
            }

        return usersLiveData.value
    }

    fun updateUsers(listener: LoadUserListener? = null){
        Loader().apply {
            loadUsers {
                users ->
                usersLiveData.value = users
                if(usersLiveData.value == null)
                    listener?.onFailure()
                else
                    listener?.onUpdated(usersLiveData.value!!)
            }
        }
    }

    interface LoadUserListener{
        fun onFailure()
        fun onLoaded(users: Users)
        fun onUpdated(users: Users)
    }
}