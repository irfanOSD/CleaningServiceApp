package com.irfan.cleaningserviceapp.model

data class Booking(
    val serviceName: String = "",
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val date: String = "",
    val time: String = "",
    val note: String = "",
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending"
)