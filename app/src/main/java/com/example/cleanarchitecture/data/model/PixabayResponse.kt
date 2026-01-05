package com.example.cleanarchitecture.data.model

data class PixabayResponse(
    val hits:List<Hit>,
    val total: Int,
    val totalHits: Int
)