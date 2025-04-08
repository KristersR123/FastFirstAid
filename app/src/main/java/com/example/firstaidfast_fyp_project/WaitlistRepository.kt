package com.example.firstaidfast_fyp_project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * WaitlistRepository is responsible for interacting with the WaitlistApi
 * to fetch data about hospital wait times and full waitlist entries.
 * It uses Retrofit for network calls and Gson for JSON serialisation/deserialisation.
 */
object WaitlistRepository {

    // Creates a lazy-initialised Retrofit instance for making HTTP requests.
    // The baseUrl points to the Render server endpoint (hospitalkiosk.onrender.com).
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://hospitalkiosk.onrender.com/") // The base URL for all API endpoints
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON responses to Kotlin objects
            .build()
    }

    // Creates a lazy-initialised WaitlistApi interface from the Retrofit instance.
    private val api: WaitlistApi by lazy {
        retrofit.create(WaitlistApi::class.java)
    }

    /**
     * fetchHospitalWaitTime calls the correct endpoint depending on hospital name.
     */
    suspend fun fetchHospitalWaitTime(hospital: String): HospitalQueueSummary {
        return when (hospital) {
            "St. James's Hospital" -> api.getHospitalAWaitTime()
            "Cork University Hospital" -> api.getHospitalBWaitTime()
            else -> HospitalQueueSummary(0, 0)
        }
    }

    /**
     * fetchWaitlist returns the live waitlist for the requested hospital.
     */
    suspend fun fetchWaitlist(hospital: String): List<WaitlistItem> {
        return when (hospital) {
            "St. James's Hospital" -> api.getHospitalAWaitlist()
            "Cork University Hospital" -> api.getHospitalBWaitlist()
            else -> emptyList()
        }
    }
}