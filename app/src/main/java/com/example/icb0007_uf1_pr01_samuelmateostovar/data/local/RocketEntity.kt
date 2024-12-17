package com.example.icb0007_uf1_pr01_samuelmateostovar.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Dimension

@Entity
data class RocketEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val active: Boolean,
    val costPerLaunch: Long,
    val successRatePct: Int,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    @Embedded(prefix = "height_") val height: Dimension,
    @Embedded(prefix = "diameter_") val diameter: Dimension,
    val isLocal: Boolean = false
)