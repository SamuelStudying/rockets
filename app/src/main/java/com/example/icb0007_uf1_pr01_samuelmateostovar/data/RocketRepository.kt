package com.example.icb0007_uf1_pr01_samuelmateostovar.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.local.AppDatabase
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.local.RocketEntity
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Dimension
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.RetrofitInstance
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Rocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RocketRepository(context : Context) {

    private val rocketDao = AppDatabase.getDatabase(context).rocketDao()

    fun getRockets(): Flow<List<RocketUi>> = flow {

        val localData = rocketDao.getAll()
        if (localData.isNotEmpty()) {
            emit(localData.map { it.toUiModel() })
        }

        try {
            val apiResponse = RetrofitInstance.api.getRockets().execute()

            if (apiResponse.isSuccessful) {
                val rocketsApi = apiResponse.body() ?: emptyList()

                val localIds = localData.map { it.id }
                val rocketsToInsert = rocketsApi.filter { it.id !in localIds }.map { it.toEntity() }

                if (rocketsToInsert.isNotEmpty()) {
                    rocketDao.insertAll(rocketsToInsert)
                }

                emit(rocketDao.getAll().map { it.toUiModel() })
            }
        } catch (e: Exception) {
            Log.e("RocketRepository", "Error al cargar los cohetes: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addLocalRocket(rocket: RocketUi) {
        rocketDao.insert(rocket.toEntity())
    }

    suspend fun updateRocket(updatedRocket: RocketUi) {
        val entity = updatedRocket.toEntity()
        rocketDao.updateRocket(entity)
    }

    suspend fun deleteLocalRocket(id: String) {
        val rocket = rocketDao.getById(id)

        if (rocket.isLocal) {
            rocketDao.deleteById(id)
        }
    }

    suspend fun getRocketById(id: String): RocketEntity {
        return rocketDao.getById(id)
    }
}

fun RocketEntity.toUiModel() : RocketUi {
    return RocketUi(
        id = id,
        name = name,
        type = type,
        active = active,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        height = height,
        diameter = diameter,
        isLocal = isLocal
    )
}

fun Rocket.toEntity() : RocketEntity {
    return RocketEntity(
        id = id,
        name = name,
        type = type,
        active = active,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        height = height,
        diameter = diameter,
        isLocal = false
    )
}

fun RocketUi.toEntity(): RocketEntity {
    return RocketEntity(
        id = id,
        name = name,
        type = type,
        active = active,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        height = height,
        diameter = diameter,
        isLocal = isLocal
    )
}