package com.example.icb0007_uf1_pr01_samuelmateostovar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {

    @Query("SELECT * FROM RocketEntity WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RocketEntity

    @Query("SELECT * FROM RocketEntity")
    suspend fun getAll(): List<RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rocketsToInsert: List<RocketEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rocket: RocketEntity)

    @Update
    suspend fun updateRocket(rocket: RocketEntity)

    @Query("DELETE FROM RocketEntity WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM RocketEntity")
    suspend fun clearAll()
}