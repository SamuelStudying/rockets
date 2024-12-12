package com.example.icb0007_uf1_pr01_samuelmateostovar.data

import android.content.Context
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.local.AppDatabase
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.local.RocketEntity
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.RetrofitInstance
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Rocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RocketRepository(private val context : Context) {

    private val rocketDao = AppDatabase.getDatabase(context).rocketDao()

    fun getRockets(): Flow<List<RocketUi>> = flow {

        val localData = rocketDao.getAll()
        if (localData.isNotEmpty()) {
            emit(localData.map { it.toUiModel() })
            return@flow
        }

        val apiResponse = RetrofitInstance.api.getRockets().execute()
        if (apiResponse.isSuccessful) {
            val rockets = apiResponse.body() ?: emptyList()
            val rocketEntities = rockets.map { it.toEntity() }

            rocketDao.insertAll(rocketEntities)

            emit(rocketEntities.map { it.toUiModel() })
        } else {
            throw Exception("ERROR: Ha fallado la conexión con la API")
        }
    }.flowOn(Dispatchers.IO)
}

fun RocketEntity.toUiModel() : RocketUi {
    return RocketUi(
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
        meters = height.meters,
        feet = height.feet
    )
}

fun Rocket.toEntity() : RocketEntity {
    return RocketEntity(
        id = 0,
        name = name,
        type = type,
        active = active,
        costPerLaunch = costPerLaunch.toInt(),
        successRatePct = successRatePct,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        height = height,
        diameter = diameter,
        meters = height.meters,
        feet = height.feet
    )
}