package com.example.firstaidfast_fyp_project

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class HospitalQueueSummary(
    val totalWait: Int,
    val patientCount: Int
)

data class WaitlistItem(
    val patientID: String,
    val condition: String,
    val severity: String,
    val queueNumber: Int,
    val estimatedWaitTime: Int,
    val status: String
)

data class Hospital(
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    fun calculateDistance(userLat: Double, userLon: Double): Double {
        val radius = 6371.0
        val latDiff = Math.toRadians(latitude - userLat)
        val lonDiff = Math.toRadians(longitude - userLon)

        val a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(latitude)) *
                Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return radius * c
    }
}

fun getNearbyHospitals(userLat: Double, userLon: Double): List<Hospital> {
    val sampleHospitals = listOf(
        Hospital("St. James's Hospital", 53.3390, -6.2958),
        Hospital("Cork University Hospital", 51.9000, -8.5000),
        Hospital("Random Far Hospital", 55.0000, -9.0000)
    )

    return sampleHospitals.filter { it.calculateDistance(userLat, userLon) <= 200 }
}