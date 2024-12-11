package com.example.icb0007_uf1_pr01_samuelmateostovar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RocketDao {

    @Query("SELECT * FROM RocketEntity")
    fun getAll(): List<RocketEntity>

    @Insert
    fun insertAll(rockets: List<RocketEntity>)
}