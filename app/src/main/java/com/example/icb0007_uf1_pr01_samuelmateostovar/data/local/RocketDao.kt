package com.example.icb0007_uf1_pr01_samuelmateostovar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {

    @Query("SELECT * FROM RocketEntity")
    fun getAll(): List<RocketEntity>

    @Insert
    fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM RocketEntity")
    suspend fun clearAll()
}