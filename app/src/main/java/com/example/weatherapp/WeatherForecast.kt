package com.example.weatherapp

class WeatherForecast(
    var currentWeather: Current?,
    var dailyForecast: List<Daily>?
) {

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
}