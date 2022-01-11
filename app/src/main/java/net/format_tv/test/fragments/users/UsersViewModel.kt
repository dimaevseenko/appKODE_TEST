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

    fun getUsers(listener: LoadUserListener? = null, category: String): Users?{
        if(category.equals("all"))
            return getUsers(listener)

        val users = Users()
        getUsers(listener)?.forEach { user ->
            if(user.department.equals(category))
                users.add(user)
        }
        return users
    }

    private fun updateUsers(listener: LoadUserListener? = null){
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

    fun updateUsers(listener: LoadUserListener? = null, category: String){
        if(category.equals("all"))
            updateUsers(listener)
         else {
             Loader().loadUsers {
                 usersLiveData.value = it

                 val users = Users()
                 it?.forEach { user ->
                     if(user.department.equals(category))
                         users.add(user)
                 }
                 listener?.onUpdated(users)
             }
        }
    }

    interface LoadUserListener{
        fun onFailure()
        fun onLoaded(users: Users)
        fun onUpdated(users: Users)
    }
}