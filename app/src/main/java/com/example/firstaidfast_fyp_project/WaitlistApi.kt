package com.example.firstaidfast_fyp_project

import retrofit2.http.GET

/**
 * WaitlistApi defines the endpoints used for retrieving hospital queue
 * information from a backend service. Retrofit uses this interface to
 * generate HTTP requests.
 */
interface WaitlistApi {

    @GET("hospitalA/hospital-wait-time")
    suspend fun getHospitalAWaitTime(): HospitalQueueSummary

    @GET("hospitalB/hospital-wait-time")
    suspend fun getHospitalBWaitTime(): HospitalQueueSummary

    @GET("hospitalA/waitlist")
    suspend fun getHospitalAWaitlist(): List<WaitlistItem>

    @GET("hospitalB/waitlist")
    suspend fun getHospitalBWaitlist(): List<WaitlistItem>
}

