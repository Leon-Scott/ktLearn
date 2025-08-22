package net.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetClient {
    private val baseUrl = "http://192.168.0.25:81/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}