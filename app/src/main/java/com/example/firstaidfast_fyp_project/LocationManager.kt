package com.example.firstaidfast_fyp_project

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.annotation.SuppressLint
import com.google.android.gms.location.*

// LocationManager is responsible for requesting location permission
// and fetching the current device location using the fused location provider.
class LocationManager(private val activity: ComponentActivity) {

    // Creates a FusedLocationProviderClient to interact with device location services
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    // Registers a permission request launcher for location permission
    // If granted, automatically triggers getLocation again
    val locationPermissionRequest =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getLocation { /* no-op callback */ }
            }
        }

    /**
     * getLocation checks for permission, requests updates, and returns the result via callback.
     * Uses high-accuracy priority and 5-second intervals.
     */
    @SuppressLint("MissingPermission") // Suppressed because the permission check is done manually
    fun getLocation(onLocationFetched: (Location?) -> Unit) {
        // Checks if the required location permission is granted
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // If not granted, launches the permission request
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        // Builds a LocationRequest with high accuracy and a 5-second interval
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()

        // Creates a callback that receives location updates
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Removes updates after the first successful location result
                fusedLocationClient.removeLocationUpdates(this)
                // Gets the last known location from the location result
                val location = locationResult.lastLocation
                // Passes the location to the provided callback function
                onLocationFetched(location)
            }
        }

        // Requests location updates with the specified request and callback
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, activity.mainLooper)
    }
}
