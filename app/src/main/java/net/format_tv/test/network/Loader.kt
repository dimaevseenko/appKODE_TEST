package net.format_tv.test.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.format_tv.test.models.RUsersResult
import net.format_tv.test.models.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Loader: Callback<RUsersResult> {

    lateinit var result: (Users?)-> Unit

    fun loadUsers(result: (Users?)-> Unit){
        this.result = result
        CoroutineScope(Dispatchers.Default).launch {
            val rUsers = Network.getRequestUsers().getUsers()
            rUsers.enqueue(this@Loader)
        }
    }

    override fun onResponse(call: Call<RUsersResult>, response: Response<RUsersResult>) {
        result(response.body()?.items)
    }

    override fun onFailure(call: Call<RUsersResult>, t: Throwable) {
        result(null)
    }
}