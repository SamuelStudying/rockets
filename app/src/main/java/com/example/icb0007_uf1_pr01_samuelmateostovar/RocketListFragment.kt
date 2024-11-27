package com.example.icb0007_uf1_pr01_samuelmateostovar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RocketListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rocket_list_fragment, container, false)
        fetchRockets()
        return view
    }

    private fun fetchRockets() {
        lifecycleScope.launch {
            val apiResponse = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getRockets().execute()
            }

            when {
                apiResponse.isSuccessful -> {
                    Log.d("RocketListFragment", "Success: ${apiResponse.body()}")
                }
                else -> {
                    Log.e("RocketListFragment", "Error: ${apiResponse.errorBody()}")
                }
            }
        }
    }
}