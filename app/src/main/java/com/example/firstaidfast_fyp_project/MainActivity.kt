package com.example.firstaidfast_fyp_project

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // For example, show the queue summary screen
                HospitalQueueScreen()
            }
        }
    }
}

//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//    @SuppressLint("MissingPermission")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        // Check if the app has location permissions
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED) {
//            // Request permission if not granted
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
//            )
//        } else {
//            // If permission is granted, get the current location
//            getCurrentLocation()
//        }
//    }
//
//    // Function to get the current location using FusedLocationProviderClient
//    @SuppressLint("MissingPermission")
//    private fun getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                if (location != null) {
//                    val userLat = location.latitude
//                    val userLon = location.longitude
//                    val accuracy = location.accuracy  // Get the accuracy in meters
//
//                    Log.d("Location", "Latitude: $userLat, Longitude: $userLon, Accuracy: $accuracy meters")
//
//                    // Now, you can use this accuracy to check if the location is reliable
//                    setContent {
//                        MaterialTheme {
//                            NearbyHospitalsScreen(userLat, userLon)
//                        }
//                    }
//                } else {
//                    Log.e("Location", "Location is null")
//                }
//            }
//    }
//
//    // Handle permission request result
//    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocation() // Permission granted, fetch location
//        } else {
//            Log.e("Location", "Permission denied")
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NearbyHospitalsScreen(userLat: Double, userLon: Double) {
//    val hospitals = getNearbyHospitals(userLat, userLon)
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Nearby Hospitals") })
//        }
//    ) { paddingValues ->
//        if (hospitals.isEmpty()) {
//            Text(
//                text = "No hospitals within 200 km.",
//                modifier = Modifier
//                    .padding(paddingValues)
//                    .fillMaxSize(),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        } else {
//            LazyColumn(
//                modifier = Modifier.padding(paddingValues)
//            ) {
//                items(hospitals) { hospital ->
//                    HospitalRow(hospital, userLat, userLon)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HospitalRow(hospital: Hospital, userLat: Double, userLon: Double) {
//    val distance = hospital.calculateDistance(userLat, userLon)
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .height(80.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column {
//            Text(
//                text = hospital.name,
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Text(
//                text = "Distance: %.2f km".format(distance),
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}
//
//data class Hospital(
//    val name: String,
//    val latitude: Double,
//    val longitude: Double
//) {
//    // Calculate the distance to the user's location (userLat, userLon) in kilometers
//    fun calculateDistance(userLat: Double, userLon: Double): Double {
//        val radius = 6371.0 // Radius of Earth in kilometers
//        val latDiff = Math.toRadians(latitude - userLat)
//        val lonDiff = Math.toRadians(longitude - userLon)
//
//        val a = sin(latDiff / 2) * sin(latDiff / 2) +
//                cos(Math.toRadians(userLat)) * cos(Math.toRadians(latitude)) *
//                sin(lonDiff / 2) * sin(lonDiff / 2)
//
//        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
//        return radius * c // returns distance in kilometers
//    }
//}
//
//fun getNearbyHospitals(userLat: Double, userLon: Double): List<Hospital> {
//    val sampleHospitals = listOf(
//        // Correct coordinates for St. James's Hospital
//        Hospital("St. James's Hospital", 53.3390, -6.2958), // Dublin (corrected)
//        Hospital("Cork University Hospital", 51.9000, -8.5000), // Cork
//        Hospital("Mater Misericordiae University Hospital", 53.3620, -6.2769), // Dublin
//        Hospital("St. Vincent's University Hospital", 53.3000, -6.2000) // Dublin
//    )
//
//    return sampleHospitals.filter { hospital ->
//        val distance = hospital.calculateDistance(userLat, userLon)
//        distance <= 200 // Filter hospitals within 200 km
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MaterialTheme {
//        NearbyHospitalsScreen(userLat = 52.6742, userLon = -7.7983) // Example coordinates
//    }
//}
