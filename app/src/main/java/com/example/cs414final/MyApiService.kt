package com.example.cs414final

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

//"https://api.open-meteo.com/v1/forecast?latitude=$latInput&longitude=$longInput&hourly=temperature_2m,rain&daily=temperature_2m_max,temperature_2m_min&current_weather=true&forecast_days=1&start_date=$date&end_date=$date7&timezone=America%2FNew_York"
//
interface ForecastService {
    @GET("forecast")
    fun getForecast(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly: String,
        @Query("daily") daily: String,
        @Query("current_weather") currentWeather: Boolean,
        @Query("forecast_days") forecastDays: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("timezone") timezone: String
    ): Call<ForecastResponse>
}