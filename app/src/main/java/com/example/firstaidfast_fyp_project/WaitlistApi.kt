package com.example.firstaidfast_fyp_project

import retrofit2.http.GET

/**
 * WaitlistApi defines the endpoints used for retrieving hospital queue
 * information from a backend service. Retrofit uses this interface to
 * generate HTTP requests.
 */
interface WaitlistApi {
<<<<<<< HEAD

    /**
     * GET endpoint that fetches a HospitalQueueSummary object
     * containing total wait time and patient count.
     */
    @GET("hospital-wait-time")
    suspend fun getHospitalWaitTime(): HospitalQueueSummary

    /**
     * GET endpoint that returns a list of WaitlistItem objects,
     * representing each patient's current waitlist status.
     */
    @GET("waitlist")
    suspend fun getWaitlist(): List<WaitlistItem>
}

/*
Explanation in the project context:

- This interface is part of the Retrofit setup for making HTTP requests
  to a remote server or local backend that manages hospital wait times.

- The "hospital-wait-time" endpoint returns summary-level data such as
  the total cumulative wait time of all patients and the number of patients
  in queue.

- The "waitlist" endpoint returns a detailed list of all queued patients,
  each with fields like patientID, severity, and estimated wait time.

- Both endpoints are defined as suspend functions, meaning they can be
  called within Kotlin coroutines for asynchronous, non-blocking operations.
*/
=======
    @GET("hospitalA/hospital-wait-time")
    suspend fun getHospitalAWaitTime(): HospitalQueueSummary

    @GET("hospitalB/hospital-wait-time")
    suspend fun getHospitalBWaitTime(): HospitalQueueSummary

    @GET("hospitalA/waitlist")
    suspend fun getHospitalAWaitlist(): List<WaitlistItem>

    @GET("hospitalB/waitlist")
    suspend fun getHospitalBWaitlist(): List<WaitlistItem>
}
>>>>>>> 6cd7d03 (added hospitaldetail screen + google navigation)
