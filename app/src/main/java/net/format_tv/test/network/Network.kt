package net.format_tv.test.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    fun getRequestUsers(): RUsers{
        return getRetrofit()
            .create(RUsers::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stoplight.io/mocks/kode-education/trainee-test/25143926/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}