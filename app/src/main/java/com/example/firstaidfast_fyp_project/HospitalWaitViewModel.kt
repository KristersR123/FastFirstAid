package com.example.firstaidfast_fyp_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed class representing different UI states for a waitlist screen
sealed class WaitlistUiState {
    // Represents a loading state (e.g., when network call is in progress)
    object Loading : WaitlistUiState()

    // Represents an error state with a message describing what went wrong
    data class Error(val message: String) : WaitlistUiState()

    // Holds summary information about total wait time and patient count
    data class SummarySuccess(val totalWait: Int, val patientCount: Int) : WaitlistUiState()

    // Holds the full list of waitlist items when successfully fetched
    data class FullWaitlistSuccess(val items: List<WaitlistItem>) : WaitlistUiState()
}

/**
 * HospitalWaitViewModel manages fetching and updating waitlist data in real time.
 * Inherits from ViewModel for Android’s Lifecycle management.
 */
class HospitalWaitViewModel : ViewModel() {

    // Private mutable state flow representing the current UI state
    private val _uiState = MutableStateFlow<WaitlistUiState>(WaitlistUiState.Loading)
    // Public read-only state flow that UI components observe
    val uiState = _uiState.asStateFlow()

    /**
     * Loads the hospital wait time summary (e.g., total wait + patient count) once.
     */
    fun loadHospitalWaitTimeOnce() {
        // Sets the state to Loading before the network request
        _uiState.value = WaitlistUiState.Loading

        // viewModelScope.launch ensures coroutine is canceled if ViewModel is cleared
        viewModelScope.launch {
            try {
                // Fetch summary from the repository (assumed to be a network or database call)
                val summary = WaitlistRepository.fetchHospitalWaitTime()

                // On success, update the state to SummarySuccess with the fetched data
                _uiState.value = WaitlistUiState.SummarySuccess(
                    totalWait = summary.totalWait,
                    patientCount = summary.patientCount
                )
            } catch (e: Exception) {
                // On error, update the state to Error with a localied message if available
                _uiState.value = WaitlistUiState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    /**
     * Starts a loop that refreshes the hospital wait time summary every 30 seconds.
     */
    fun startRealtimeUpdates() {
        // Launches a long-running coroutine tied to the ViewModel’s lifecycle
        viewModelScope.launch {
            while (true) {
                // Triggers a single fetch of the hospital wait time
                loadHospitalWaitTimeOnce()

                // Delays for 30 seconds to wait until the next update
                delay(30000L)
            }
        }
    }

    /**
     * Loads the full waitlist data and updates UI state accordingly.
     */
    fun loadFullWaitlist() {
        // Sets the UI state to Loading to indicate a fetch is happening
        _uiState.value = WaitlistUiState.Loading

        // Starts a coroutine in the ViewModel’s scope
        viewModelScope.launch {
            try {
                // Fetches the full waitlist from the repository
                val items = WaitlistRepository.fetchWaitlist()

                // Updates state to FullWaitlistSuccess with the retrieved items
                _uiState.value = WaitlistUiState.FullWaitlistSuccess(items)
            } catch (e: Exception) {
                // If an error occurs, updates state to Error with a relevant message
                _uiState.value = WaitlistUiState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }
}
