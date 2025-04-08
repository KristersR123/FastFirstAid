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
import com.example.firstaidfast_fyp_project.ui.theme.FirstAidFastFYP_ProjectTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
<<<<<<< HEAD
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * MainActivity is the entry point for the Android application.
 * It fetches the user's location and then renders a HospitalQueueScreen
 * with the obtained latitude and longitude.
 */
=======

>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
class MainActivity : ComponentActivity() {

    // Instance of LocationManager to manage location permission and fetching
    private lateinit var locationManager: LocationManager

    /**
     * onCreate is called when the activity is first created.
     * Sets up the LocationManager and requests location.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialses the locationManager with the current activity context
        locationManager = LocationManager(this)

        // Asynchronously fetches the device's location
        locationManager.getLocation { location ->
            // Checks if a valid location was obtained
            location?.let {
                // Extracts latitude and longitude from the Location object
                val userLat = it.latitude
                val userLon = it.longitude

                // Logs the received latitude and longitude
                Log.d("MainActivity", "Location received: ($userLat, $userLon)")

                // Sets up the Jetpack Compose content
                setContent {
                    // Applies a Material 3 theme to the composition
                    FirstAidFastFYP_ProjectTheme {
<<<<<<< HEAD
                        // Displays a HospitalQueueScreen, passing the location coordinates
                        HospitalQueueScreen(userLat = userLat, userLon = userLon)
=======
                        Navigation(userLat = userLat, userLon = userLon)
>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
                    }
                }
            } ?: Log.e("MainActivity", "Failed to fetch location")
            // If location is null, logs an error message
        }
    }
}
<<<<<<< HEAD
=======

>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
