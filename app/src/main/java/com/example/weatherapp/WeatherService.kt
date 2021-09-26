package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.*

interface WeatherService {
    @GET("data/2.5/onecall?")
    fun getCurrentWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: List<String>,
        @Query("appid") appid: String): Call<WeatherResponse>

}