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

    private val _isRocketDeleted = MutableStateFlow(false)
    val isRocketDeleted: StateFlow<Boolean> = _isRocketDeleted

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

    fun updateRocket(updatedRocket: RocketUi) {
        viewModelScope.launch {
            repository.updateRocket(updatedRocket)
            fetchRockets()
        }
    }

    fun deleteRocket(id: String) {
        viewModelScope.launch {
            val rocket = repository.getRocketById(id)
            if (rocket.isLocal) {
                repository.deleteLocalRocket(id)
                _isRocketDeleted.value = true
            } else {
                _isRocketDeleted.value = false
            }
        }
    }

    fun resetRocketDeletedState() {
        _isRocketDeleted.value = false
    }
}
