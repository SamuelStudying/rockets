package com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val repository : RocketRepository) : ViewModel() {

    private val _rocketUiList = MutableStateFlow<List<RocketUi>>(emptyList())
    val rocketUiList : StateFlow<List<RocketUi>> = _rocketUiList

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState : StateFlow<String?> = _errorState

    fun fetchRockets() {
        viewModelScope.launch {
            repository.getRockets().catch { error ->
                _errorState.value = error.message
            }
                .collect { rockets ->
                    _rocketUiList.value = rockets
                }
        }
    }
}
