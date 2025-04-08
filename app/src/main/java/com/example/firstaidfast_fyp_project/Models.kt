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
    val status: String,
    val wasSeen: Boolean
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
        Hospital("St. James's Hospital", 53.3407, -6.2949),
        Hospital("Cork University Hospital", 51.8827, -8.5122),
        Hospital("My House", 52.63741236733832, -7.628223896026611)
    )

    return sampleHospitals.filter { it.calculateDistance(userLat, userLon) <= 200 }
}