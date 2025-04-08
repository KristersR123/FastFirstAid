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

<<<<<<< HEAD
    // Creates a lazy-initialised WaitlistApi interface from the Retrofit instance.
    // This interface defines the endpoints for fetching hospital wait info.
    private val waitlistApi: WaitlistApi by lazy {
        retrofit.create(WaitlistApi::class.java)
    }

    /**
     * fetchHospitalWaitTime calls the WaitlistApi to get a summary
     * of hospital wait information (total wait and patient count).
     */
    suspend fun fetchHospitalWaitTime(): HospitalQueueSummary {
        return waitlistApi.getHospitalWaitTime()
    }

    /**
     * fetchWaitlist calls the WaitlistApi to retrieve a detailed list
     * of patients currently in the waitlist, including estimated times.
     */
    suspend fun fetchWaitlist(): List<WaitlistItem> {
        return waitlistApi.getWaitlist()
=======
    private val api: WaitlistApi by lazy {
        retrofit.create(WaitlistApi::class.java)
    }

    suspend fun fetchHospitalWaitTime(hospital: String): HospitalQueueSummary {
        return when (hospital) {
            "St. James's Hospital" -> api.getHospitalAWaitTime()
            "Cork University Hospital" -> api.getHospitalBWaitTime()
            else -> HospitalQueueSummary(0, 0)
        }
    }

    suspend fun fetchWaitlist(hospital: String): List<WaitlistItem> {
        return when (hospital) {
            "St. James's Hospital" -> api.getHospitalAWaitlist()
            "Cork University Hospital" -> api.getHospitalBWaitlist()
            else -> emptyList()
        }
>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
    }
}

/*
Explanation in the project context:
- This repository acts as a layer that the ViewModel (or other classes) can call
  to fetch data from the remote server without having to deal directly with
  Retrofit code.
- The calls are marked suspend to be used within coroutines for asynchronous
  operations.
- The baseUrl and endpoints correspond to the hospital wait time API on a
  hosting service (Render).
*/
