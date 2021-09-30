package com.example.weatherapp

import android.os.Parcel
import android.os.Parcelable

class WeatherForecast(
    var latWeather: Float?,
    var lonWeather: Float?): Parcelable {

    private var currentWeather: Current? = null
    private var dailyForecast: List<Daily>? = null

    private constructor(parcel: Parcel) : this(
        latWeather = parcel.readFloat(),
        lonWeather = parcel.readFloat()
    )

    fun getDailyForecast(): List<Daily>?{
        return dailyForecast
    }

    fun setCurrentWeather(currentWeather: Current?){
        this@WeatherForecast.currentWeather = currentWeather
    }

    fun setDailyForecast(dailyForecast: List<Daily>?){
        this@WeatherForecast.dailyForecast = dailyForecast
    }

    fun getCurrentTemperature(): Int? {
            return currentWeather?.temp?.let { convertKelvinsToCelsius(it)}
        }

    fun getMinMaxTemperature(): String {
            val iconCelsius = "\u00B0"
            val minTemp = dailyForecast?.get(0)?.temp?.min?.let { convertKelvinsToCelsius(it) }
            val maxTemp = dailyForecast?.get(0)?.temp?.max?.let { convertKelvinsToCelsius(it) }
            return maxTemp.toString() + iconCelsius + " / " + minTemp.toString() + iconCelsius
    }

    fun getCurrentWeather(): String? {
        return currentWeather?.weather?.get(0)?.main
    }

    private fun convertKelvinsToCelsius(temp: Float): Int{
            val kelvinsTemp: Float = 273.15F
            return temp.minus(kelvinsTemp).toInt()
        }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        latWeather?.let { parcel.writeFloat(it) }
        lonWeather?.let { parcel.writeFloat(it)}
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherForecast> {
        override fun createFromParcel(parcel: Parcel): WeatherForecast {
            return WeatherForecast(parcel)
        }
        override fun newArray(size: Int): Array<WeatherForecast?> {
            return arrayOfNulls(size)
        }
    }
}