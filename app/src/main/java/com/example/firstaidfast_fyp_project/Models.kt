package com.example.firstaidfast_fyp_project

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// Represents a summary of the hospital queue: total wait time and count of patients
data class HospitalQueueSummary(
    val totalWait: Int,     // The sum of estimated wait times for all queueing patients
    val patientCount: Int   // The number of patients currently in queue
)

// Represents a single entry/item in the hospital waitlist
data class WaitlistItem(
<<<<<<< HEAD
    val patientID: String,        // Unique ID assigned to the patient
    val condition: String,        // Patient's condition
    val severity: String,         // Severity category for the condition
    val queueNumber: Int,         // The queue position assigned to the patient
    val estimatedWaitTime: Int,   // The current estimated wait time for this patient
    val status: String            // Current status, e.g. "Queueing for", "With Doctor", etc.
=======
    val patientID: String,
    val condition: String,
    val severity: String,
    val queueNumber: Int,
    val estimatedWaitTime: Int,
    val status: String,
    val wasSeen: Boolean
>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
)

// Represents a hospital with location coordinates
data class Hospital(
    val name: String,       // The hospital's name
    val latitude: Double,   // The hospital's latitude coordinate
    val longitude: Double   // The hospital's longitude coordinate
) {
    /**
     * Calculates the distance (in kilometers) from the userLat/userLon to
     * this hospital's latitude/longitude using the Haversine formula.
     */
    fun calculateDistance(userLat: Double, userLon: Double): Double {
        val radius = 6371.0 // Earth's radius in km

        // Differences in latitude and longitude in radians
        val latDiff = Math.toRadians(latitude - userLat)
        val lonDiff = Math.toRadians(longitude - userLon)

        // Haversine formula components
        val a = sin(latDiff / 2) * sin(latDiff / 2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(latitude)) *
                sin(lonDiff / 2) * sin(lonDiff / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        // Distance in kilometers
        return radius * c
    }
}

/**
 * getNearbyHospitals returns a list of sample hospitals within 200 km of the user's location.
 * Adjust the distance threshold or hospital list as needed.
 */
fun getNearbyHospitals(userLat: Double, userLon: Double): List<Hospital> {
    // A predefined sample list of hospitals
    val sampleHospitals = listOf(
        Hospital("St. James's Hospital", 53.3407, -6.2949),
        Hospital("Cork University Hospital", 51.8827, -8.5122),
        Hospital("My House", 52.63741236733832, -7.628223896026611)
    )

    // Filters the sample hospitals by a 200 km distance threshold
    return sampleHospitals.filter { it.calculateDistance(userLat, userLon) <= 200 }
}
