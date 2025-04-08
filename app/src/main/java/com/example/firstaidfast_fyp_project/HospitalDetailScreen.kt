package com.example.firstaidfast_fyp_project

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.firstaidfast_fyp_project.formatTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalDetailScreen(
    hospitalName: String,
    navController: NavController,
    hospitalLat: Double,
    hospitalLon: Double
) {
    var waitlist by remember { mutableStateOf<List<WaitlistItem>>(emptyList()) }

    LaunchedEffect(hospitalName) {
        try {
            waitlist = WaitlistRepository.fetchWaitlist(hospitalName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        delay(5000L)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Condition Breakdown") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Home, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val grouped = waitlist
            .filter { it.status != "With Doctor" && it.status != "Discharged" && !it.wasSeen }
            .groupBy { it.condition }
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                if (grouped.isEmpty()) {
                    item {
                        Text("No patients currently in the waitlist for $hospitalName.")
                    }
                } else {
                    grouped.forEach { (condition, items) ->
                        val bySeverity = items.groupBy { it.severity }

                        item {
                            Text(
                                text = "🩺 $condition",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
                            )
                        }

                        bySeverity.forEach { (severity, group) ->
                            val totalWait = group.sumOf { it.estimatedWaitTime }

                            item {
                                Text(
                                    text = "• $severity: ${group.size} patient(s), total wait ${formatTime(totalWait)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val uri = Uri.parse("google.navigation:q=$hospitalLat,$hospitalLon")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    try {
                        navController.context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            navController.context,
                            "Google Maps not installed.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text("Navigate to Hospital")
            }
        }
    }
}