package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.*

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appid: String): Call<WeatherResponse>
}