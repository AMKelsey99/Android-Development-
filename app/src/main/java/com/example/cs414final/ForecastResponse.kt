package com.example.cs414final

data class ForecastResponse(
    val latitude: Float,
    val longitude: Float,
    val hourly: Map<String, Any>,
    val daily: Map<String, Any>,
    val current_weather: Map<String, Any>,
    val timezone: String
)