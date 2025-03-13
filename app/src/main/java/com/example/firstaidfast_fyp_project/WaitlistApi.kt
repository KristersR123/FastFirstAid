package com.example.firstaidfast_fyp_project

import retrofit2.http.GET

interface WaitlistApi {
    @GET("hospital-wait-time")
    suspend fun getHospitalWaitTime(): HospitalQueueSummary

    @GET("waitlist")
    suspend fun getWaitlist(): List<WaitlistItem>
}