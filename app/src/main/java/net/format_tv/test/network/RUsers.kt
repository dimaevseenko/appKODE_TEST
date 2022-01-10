package net.format_tv.test.network

import net.format_tv.test.models.RUsersResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RUsers {
    @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, example=success"
    )
    @GET("users")
    fun getUsers(): Call<RUsersResult>
}