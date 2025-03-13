package com.example.firstaidfast_fyp_project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WaitlistRepository {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://hospitalkiosk.onrender.com/")  // Render server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val waitlistApi: WaitlistApi by lazy {
        retrofit.create(WaitlistApi::class.java)
    }

    suspend fun fetchHospitalWaitTime(): HospitalQueueSummary {
        return waitlistApi.getHospitalWaitTime()
    }

    suspend fun fetchWaitlist(): List<WaitlistItem> {
        return waitlistApi.getWaitlist()
    }
}