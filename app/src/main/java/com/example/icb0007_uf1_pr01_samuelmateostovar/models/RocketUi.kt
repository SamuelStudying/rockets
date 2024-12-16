package com.example.icb0007_uf1_pr01_samuelmateostovar.models

import android.os.Parcelable
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Dimension
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketUi(
    val name: String,
    val type: String,
    val active: Boolean,
    val costPerLaunch: Long,
    val successRatePct: Int,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val height: Dimension,
    val diameter: Dimension,
    val meters: Double,
    val feet: Double,
    val isLocal: Boolean
) : Parcelable
