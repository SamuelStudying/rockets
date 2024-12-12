package com.example.icb0007_uf1_pr01_samuelmateostovar

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val rocketUiList = MutableStateFlow<List<RocketUi>>(emptyList())
    val rocketUiListFlow = rocketUiList.asStateFlow()

    fun fetchRockets(context: Context) {
        viewModelScope.launch {

            if (rocketUiList.value.isNotEmpty()) {
                return@launch
            }

            val db = AppDatabase.getDatabase(context)
            val rocketDao = db.rocketDao()
            val rocketEntities = withContext(Dispatchers.IO) { rocketDao.getAll() }

            if (rocketEntities.isNotEmpty()) {
                val rocketUiMapped = rocketEntities.map { rocketEntity ->
                    RocketUi(
                        name = rocketEntity.name,
                        type = rocketEntity.type,
                        active = rocketEntity.active,
                        costPerLaunch = rocketEntity.costPerLaunch,
                        successRatePct = rocketEntity.successRatePct,
                        country = rocketEntity.country,
                        company = rocketEntity.company,
                        wikipedia = rocketEntity.wikipedia,
                        description = rocketEntity.description,
                        height = rocketEntity.height,
                        diameter = rocketEntity.diameter,
                        meters = rocketEntity.height.meters,
                        feet = rocketEntity.height.feet
                    )
                }
                rocketUiList.value = rocketUiMapped
                return@launch
            }

            val apiResponse = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getRockets().execute()
            }

            when {
                apiResponse.isSuccessful -> {
                    val rockets = apiResponse.body() ?: emptyList()
                    val rocketListMappedEntity = rockets.map { rocket ->
                        RocketEntity(
                            id = 0,
                            name = rocket.name,
                            type = rocket.type,
                            active = rocket.active,
                            costPerLaunch = rocket.costPerLaunch.toInt(),
                            successRatePct = rocket.successRatePct,
                            country = rocket.country,
                            company = rocket.company,
                            wikipedia = rocket.wikipedia,
                            description = rocket.description,
                            height = rocket.height,
                            diameter = rocket.diameter,
                            meters = rocket.height.meters,
                            feet = rocket.diameter.feet
                        )
                    }

                    withContext(Dispatchers.IO) { rocketDao.insertAll(rocketListMappedEntity) }
                    val rocketListDatabase = withContext(Dispatchers.IO) { rocketDao.getAll() }
                    val rocketListMapped = rocketListDatabase.map { rocketEntity ->
                        RocketUi(
                            name = rocketEntity.name,
                            type = rocketEntity.type,
                            active = rocketEntity.active,
                            costPerLaunch = rocketEntity.costPerLaunch,
                            successRatePct = rocketEntity.successRatePct,
                            country = rocketEntity.country,
                            company = rocketEntity.company,
                            wikipedia = rocketEntity.wikipedia,
                            description = rocketEntity.description,
                            height = rocketEntity.height,
                            diameter = rocketEntity.diameter,
                            meters = rocketEntity.height.meters,
                            feet = rocketEntity.height.feet
                        )
                    }

                    rocketUiList.value = rocketListMapped
                }
                else -> {
                    Log.e("RocketListFragment", "Error: ${apiResponse.errorBody()}")
                }
            }
        }
    }
}
