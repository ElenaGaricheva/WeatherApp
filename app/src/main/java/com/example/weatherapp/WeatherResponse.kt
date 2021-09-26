package com.example.weatherapp

import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
    @SerializedName("lon")
    var lon: Float = 0.toFloat()
    @SerializedName("timezone")
    var timezone: String? = null
    @SerializedName("timezone_offset")
    var timezoneOffset: String? = null
    @SerializedName("current")
    var current: Current? = null
    @SerializedName("daily")
    var daily: List<Daily>? = null
}

class Current {
    @SerializedName("clouds")
    var clouds: Int = 0
    @SerializedName("dew_point")
    var dewPoint: Float? = 0.toFloat()
    @SerializedName("dt")
    var dt: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("feels_like")
    var feelsLike: Float? = 0.toFloat()
    @SerializedName("uvi")
    var uvi: Float? = 0.toFloat()
    @SerializedName("visibility")
    var visibility: String? = null
    @SerializedName("weather")
    var weather: Weather? = null
    @SerializedName("wind_deg")
    var windDeg: Int = 0
    @SerializedName("wind_gust")
    var windGust: Float? = 0.toFloat()
    @SerializedName("wind_speed")
    var windSpeed: Float? = 0.toFloat()
}

class Weather {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("main")
    var main: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("icon")
    var icon: String? = null
}

class Daily {
    @SerializedName("clouds")
    var clouds: Int = 0
    @SerializedName("dew_point")
    var dewPoint: Float? = 0.toFloat()
    @SerializedName("dt")
    var dt: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("moon_phase")
    var moonPhase: Float = 0.toFloat()
    @SerializedName("moonrise")
    var moonrise: String? = null
    @SerializedName("moonset")
    var moonset: String? = null
    @SerializedName("pop")
    var pop: Float? = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0
    @SerializedName("temp")
    var temp: Temp? = null
    @SerializedName("feels_like")
    var feelsLike: FeelsLike? = null
    @SerializedName("uvi")
    var uvi: Float? = 0.toFloat()
    @SerializedName("visibility")
    var visibility: String? = null
    @SerializedName("weather")
    var weather: List<Weather> = listOf()
    @SerializedName("wind_deg")
    var windDeg: Int = 0
    @SerializedName("wind_gust")
    var windGust: Float? = 0.toFloat()
    @SerializedName("wind_speed")
    var windSpeed: Float? = 0.toFloat()
}

class FeelsLike {
    @SerializedName("day")
    var day: Float? = 0.toFloat()
    @SerializedName("eve")
    var eve: Float? = 0.toFloat()
    @SerializedName("morn")
    var morn: Float? = 0.toFloat()
    @SerializedName("night")
    var night: Float? = 0.toFloat()
}

class Temp {
    @SerializedName("day")
    var day: Float? = 0.toFloat()
    @SerializedName("eve")
    var eve: Float? = 0.toFloat()
    @SerializedName("morn")
    var morn: Float? = 0.toFloat()
    @SerializedName("night")
    var night: Float? = 0.toFloat()
    @SerializedName("min")
    var min: Float? = 0.toFloat()
    @SerializedName("max")
    var max: Float? = 0.toFloat()
}