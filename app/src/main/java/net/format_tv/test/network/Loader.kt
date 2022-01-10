package net.format_tv.test.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.format_tv.test.models.Users

class Loader {
    fun loadUsers(result: (Users?)-> Unit){
        CoroutineScope(Dispatchers.Default).launch {
            val rUsers = Network.getRequestUsers().getUsers().execute().body()
            launch(Dispatchers.Main) {
                if (rUsers != null)
                    result(rUsers.items)
                else
                    result(null)
            }
        }
    }
}