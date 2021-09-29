package com.example.weatherapp

import com.google.gson.annotations.SerializedName

class CitiesResponse {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("local_names")
    var localNames: Map<String, String>? = null
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
    @SerializedName("lon")
    var lon: Float = 0.toFloat()
    @SerializedName("country")
    val country: String? = null
}