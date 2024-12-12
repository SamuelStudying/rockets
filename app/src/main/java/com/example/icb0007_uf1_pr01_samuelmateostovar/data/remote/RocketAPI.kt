package com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface RocketAPI {
    @GET("rockets")
    fun getRockets(): Call<List<Rocket>>
}