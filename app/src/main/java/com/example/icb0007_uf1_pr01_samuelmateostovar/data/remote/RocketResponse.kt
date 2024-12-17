package com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Rocket(
    val id: String,
    val height: Dimension,
    val diameter: Dimension,
    val flickrImages: List<String>,
    val name: String,
    val type: String,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Long,
    @SerializedName("success_rate_pct")
    val successRatePct: Int,
    val firstFlight: String,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String
)

@Parcelize
data class Dimension(
    val meters: Double,
    val feet: Double
) : Parcelable