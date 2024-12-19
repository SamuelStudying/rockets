package com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val repository : RocketRepository) : ViewModel() {

    private val _rocketUiList = MutableStateFlow<List<RocketUi>>(emptyList())
    val rocketUiList : StateFlow<List<RocketUi>> = _rocketUiList

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState : StateFlow<String?> = _errorState

    private val _deleteRocketEvent = MutableSharedFlow<Result<Boolean>>(replay = 0)
    val deleteRocketEvent: SharedFlow<Result<Boolean>> = _deleteRocketEvent

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

    fun addRocket(newRocket: RocketUi) {
        viewModelScope.launch {
            repository.addLocalRocket(newRocket)
            fetchRockets()
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
            try {
                val rocket = repository.getRocketById(id)
                if (rocket.isLocal) {
                    repository.deleteLocalRocket(id)
                    _deleteRocketEvent.emit(Result.success(true)) // Éxito al eliminar
                } else {
                    _deleteRocketEvent.emit(Result.success(false)) // No se puede eliminar (no local)
                }
            } catch (e: Exception) {
                _deleteRocketEvent.emit(Result.failure(e)) // Error durante la eliminación
            }
        }
    }
}
