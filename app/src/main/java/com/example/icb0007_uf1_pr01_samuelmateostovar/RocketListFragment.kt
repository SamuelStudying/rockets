package com.example.icb0007_uf1_pr01_samuelmateostovar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.adapter.RocketAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RocketListFragment : Fragment() {

    private val rocketUiList = mutableListOf<RocketUi>()
    private lateinit var rvRockets: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rocket_list_fragment, container, false)
        initRecyclerView(view)
        fetchRockets()
        return view
    }

    private fun fetchRockets() {
        lifecycleScope.launch {

            val db = AppDatabase.getDatabase(requireContext())
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
                rocketUiList.addAll(rocketUiMapped)
                rvRockets.adapter?.notifyDataSetChanged()
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

                    rocketUiList.addAll(rocketListMapped)
                    rvRockets.adapter?.notifyDataSetChanged()
                    Log.d("RocketListFragment", "Success: ${apiResponse.body()}")
                }
                else -> {
                    Log.e("RocketListFragment", "Error: ${apiResponse.errorBody()}")
                }
            }
        }
    }

    private fun initRecyclerView(view : View) {
        rvRockets = view.findViewById(R.id.rvRockets)
        rvRockets.adapter = RocketAdapter(rocketUiList)
        rvRockets.layoutManager = LinearLayoutManager(requireContext())
    }
}