package com.example.weatherapp

import android.annotation.SuppressLint
import android.Manifest.permission.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var weatherForecast: WeatherForecast? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: Float? = null
    private var longitude: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    private fun fetchLocation(){
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null){
                latitude = it.latitude.toFloat()
                longitude = it.longitude.toFloat()
                getCurrentData()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val currentTempView: TextView = findViewById(R.id.currentTemperature)
        val minMaxTempView: TextView = findViewById(R.id.minMaxTemperature)
        val currentWeather: TextView = findViewById(R.id.currentWeather)
        val dailyForecast: RecyclerView = findViewById(R.id.dailyForecast)

        dailyForecast.layoutManager = LinearLayoutManager(this)
        dailyForecast.adapter = weatherForecast?.dailyForecast?.let { ForecastAdapter(it) }

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

        val call = retrofit.getCurrentWeather(latitude, longitude, exclude, AppId)
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
