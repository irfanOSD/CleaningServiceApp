package com.irfan.cleaningserviceapp.model

data class Service(
    val id: Int,
    val name: String,
    val iconResId: Int,
    val description: String,
    val categoryId: String = ""
)