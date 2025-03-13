package com.example.firstaidfast_fyp_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed class representing different UI states
sealed class WaitlistUiState {
    object Loading : WaitlistUiState()
    data class Error(val message: String) : WaitlistUiState()
    data class SummarySuccess(val totalWait: Int, val patientCount: Int) : WaitlistUiState()
    data class FullWaitlistSuccess(val items: List<WaitlistItem>) : WaitlistUiState()
}

class HospitalWaitViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<WaitlistUiState>(WaitlistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadHospitalWaitTime() {
        _uiState.value = WaitlistUiState.Loading
        viewModelScope.launch {
            try {
                val summary = WaitlistRepository.fetchHospitalWaitTime()
                _uiState.value = WaitlistUiState.SummarySuccess(
                    totalWait = summary.totalWait,
                    patientCount = summary.patientCount
                )
            } catch (e: Exception) {
                _uiState.value = WaitlistUiState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    fun loadFullWaitlist() {
        _uiState.value = WaitlistUiState.Loading
        viewModelScope.launch {
            try {
                val items = WaitlistRepository.fetchWaitlist()
                _uiState.value = WaitlistUiState.FullWaitlistSuccess(items)
            } catch (e: Exception) {
                _uiState.value = WaitlistUiState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }
}