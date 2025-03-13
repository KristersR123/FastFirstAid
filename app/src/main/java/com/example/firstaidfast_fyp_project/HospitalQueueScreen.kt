package com.example.firstaidfast_fyp_project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalQueueScreen(
    viewModel: HospitalWaitViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadHospitalWaitTime()
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hospital Queue Info") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (uiState) {
                is WaitlistUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is WaitlistUiState.Error -> {
                    val msg = (uiState as WaitlistUiState.Error).message
                    Text(text = "Error loading wait times: $msg")
                }
                is WaitlistUiState.SummarySuccess -> {
                    val data = uiState as WaitlistUiState.SummarySuccess
                    Text(text = "Total Wait: ${data.totalWait} minutes")
                    Text(text = "Number of Patients Queueing: ${data.patientCount}")
                }
                is WaitlistUiState.FullWaitlistSuccess -> {
                    val data = uiState as WaitlistUiState.FullWaitlistSuccess
                    Text(text = "Waitlist (size: ${data.items.size}):")
                    data.items.forEach { item ->
                        Text(
                            text = "• Patient ${item.patientID} => Wait: ${item.estimatedWaitTime} min, Severity: ${item.severity}"
                        )
                    }
                }
            }
        }
    }
}