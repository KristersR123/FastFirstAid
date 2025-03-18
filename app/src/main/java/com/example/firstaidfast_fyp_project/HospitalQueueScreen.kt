package com.example.firstaidfast_fyp_project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalQueueScreen(
    viewModel: HospitalWaitViewModel = viewModel(),
    userLat: Double,
    userLon: Double
) {
    LaunchedEffect(Unit) {
        viewModel.startRealtimeUpdates()
    }

    val uiState by viewModel.uiState.collectAsState()
    val hospitals = getNearbyHospitals(userLat, userLon) // Get hospitals within 200km

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nearby Hospitals & Queues") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (hospitals.isEmpty()) {
                Text("🚫 No hospitals found within 200 km.")
            } else {
                LazyColumn {
                    items(hospitals) { hospital ->
                        // Display hospital details
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = "🏥 ${hospital.name}", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "📍 Distance: %.2f km".format(hospital.calculateDistance(userLat, userLon)),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                // **Show waitlist details only for St. James's Hospital**
                                if (hospital.name == "St. James's Hospital") {
                                    when (uiState) {
                                        is WaitlistUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                                        is WaitlistUiState.Error -> {
                                            val msg = (uiState as WaitlistUiState.Error).message
                                            Text("⚠️ Error loading wait times: $msg", modifier = Modifier.padding(8.dp))
                                        }
                                        is WaitlistUiState.SummarySuccess -> {
                                            val data = uiState as WaitlistUiState.SummarySuccess
                                            Text(
                                                text = "⏳ Total Wait: ${data.totalWait} minutes\n" +
                                                        "👥 Patients in Queue: ${data.patientCount}",
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                        else -> {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}