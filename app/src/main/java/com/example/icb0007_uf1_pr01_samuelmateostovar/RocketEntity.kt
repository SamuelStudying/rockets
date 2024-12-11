package com.example.icb0007_uf1_pr01_samuelmateostovar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RocketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val active: Boolean,
    val costPerLaunch: Int,
    val successRatePct: Int,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val height: Dimension,
    val diameter: Dimension,
    val meters: Double,
    val feet: Double
)