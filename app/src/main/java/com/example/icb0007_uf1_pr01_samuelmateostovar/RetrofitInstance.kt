package com.example.icb0007_uf1_pr01_samuelmateostovar

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.spacexdata.com/v4/"

object RetrofitInstance {
    val api: RocketAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RocketAPI::class.java)
    }
}