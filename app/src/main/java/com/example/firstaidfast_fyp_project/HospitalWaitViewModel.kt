package com.example.firstaidfast_fyp_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed class representing different UI states for a waitlist screen
sealed class WaitlistUiState {
    object Loading : WaitlistUiState()
    data class Error(val message: String) : WaitlistUiState()
    data class SummarySuccess(val totalWait: Int, val patientCount: Int) : WaitlistUiState()
    data class FullWaitlistSuccess(val items: List<WaitlistItem>) : WaitlistUiState()
}

/**
 * HospitalWaitViewModel manages fetching and updating waitlist data in real time.
 * Inherits from ViewModel for Android’s Lifecycle management.
 */
class HospitalWaitViewModel : ViewModel() {

    // Map of UI states per hospital name
    private val _uiStates = mutableMapOf<String, MutableStateFlow<WaitlistUiState>>()

    // Expose the UI state for a given hospital
    fun getUiState(hospital: String): StateFlow<WaitlistUiState> {
        return _uiStates.getOrPut(hospital) { MutableStateFlow(WaitlistUiState.Loading) }
    }

    // Fetch total wait + count once
    fun loadHospitalWaitTimeOnce(hospital: String) {
        val uiStateFlow = getUiState(hospital) as MutableStateFlow<WaitlistUiState>
        uiStateFlow.value = WaitlistUiState.Loading

        viewModelScope.launch {
            try {
                val summary = WaitlistRepository.fetchHospitalWaitTime(hospital)
                uiStateFlow.value = WaitlistUiState.SummarySuccess(
                    totalWait = summary.totalWait,
                    patientCount = summary.patientCount
                )
            } catch (e: Exception) {
                uiStateFlow.value = WaitlistUiState.Error(e.localizedMessage ?: "Error fetching wait time")
            }
        }
    }

    // Auto-refresh every 30 seconds
    fun startRealtimeUpdates(hospital: String) {
        viewModelScope.launch {
            while (true) {
                loadHospitalWaitTimeOnce(hospital)
                delay(30000L)
            }
        }
    }
}