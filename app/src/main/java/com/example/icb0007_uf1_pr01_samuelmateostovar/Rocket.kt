package com.example.icb0007_uf1_pr01_samuelmateostovar

data class Rocket(
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
    val diameter: Dimension
)

data class Dimension(
    val meters: Double,
    val feet: Double
)
