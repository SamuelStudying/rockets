package com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository

class MainViewModelFactory(private val repository: RocketRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return  MainViewModel(repository) as T
        }
        throw IllegalArgumentException("VidewModel class desconocida")
    }
}