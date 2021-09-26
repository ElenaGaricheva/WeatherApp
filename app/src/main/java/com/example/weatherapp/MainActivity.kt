package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var weatherForecast: WeatherForecast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCurrentData()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val currentTempView: TextView = findViewById(R.id.currentTemperature)
        val minMaxTempView: TextView = findViewById(R.id.minMaxTemperature)
        val currentWeather: TextView = findViewById(R.id.currentWeather)
        val dailyForecast: RecyclerView = findViewById(R.id.dailyForecast)

        dailyForecast.layoutManager = LinearLayoutManager(this)

        weatherForecast?.getCurrentTemperature()?.toString().let { currentTempView.setText(it) }
        weatherForecast?.getMinMaxTemperature().let { minMaxTempView.setText(it) }
        weatherForecast?.getCurrentWeather().let { currentWeather.setText(it) }

    }

    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        val call = retrofit.getCurrentWeather(55.7522f, 37.6156f, exclude, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    var currentData = response.body()!!
                    weatherForecast = WeatherForecast(currentData.current, currentData.daily)
                    initView()
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        }
        )
    }

    companion object {
        var BaseUrl = "https://api.openweathermap.org"
        var AppId = "a568bd37f06c1bcd1ceeae9be4e359ee"
        var exclude = listOf("minutely", "hourly", "alerts")
    }
}
