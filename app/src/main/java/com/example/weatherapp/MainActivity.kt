package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCurrentData()
    }

    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        val call = retrofit.getCurrentWeather(city, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        }
        )
    }

    companion object {
        var BaseUrl = "http://api.openweathermap.org"
        var AppId = "a568bd37f06c1bcd1ceeae9be4e359ee"
        var city = "Moscow"
    }
}
