package com.irfan.cleaningserviceapp.model

data class PopularService(
    val id: String,
    val name: String,
    val imageResId: Int,
    val price: String,
    val rating: Float
)