package com.example.firstaidfast_fyp_project

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