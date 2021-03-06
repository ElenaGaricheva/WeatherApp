package com.example.weatherapp

import android.annotation.SuppressLint
import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import android.location.Geocoder
import android.os.Parcelable
import java.util.*


class MainActivity : AppCompatActivity() {
    private var weatherForecast: WeatherForecast = WeatherForecast(0f,0f)
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (weatherForecast.latWeather == 0f && weatherForecast.lonWeather == 0f){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fetchLocation()
        }else getCurrentData()
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
                weatherForecast.latWeather = it.latitude.toFloat()
                weatherForecast.lonWeather = it.longitude.toFloat()
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
        val currentCity: TextView = findViewById(R.id.cityName)

        dailyForecast.layoutManager = LinearLayoutManager(this)
        dailyForecast.adapter = weatherForecast.getDailyForecast()?.let { ForecastAdapter(it) }
        currentCity.setText(weatherForecast.latWeather?.let { weatherForecast.lonWeather?.let { it1 -> getCurrentCityName(it.toDouble(), it1.toDouble()) } })
        weatherForecast.getCurrentTemperature()?.toString().let { currentTempView.setText(it) }
        weatherForecast.getMinMaxTemperature().let { minMaxTempView.setText(it) }
        weatherForecast.getCurrentWeather().let { currentWeather.setText(it) }

        currentCity.setOnClickListener{
            val citySearch = Intent()
            citySearch.setClass(this, SearchCity::class.java)
            citySearch.putExtra("WEATHER_FORECAST", weatherForecast)
            startActivityForResult(citySearch, 99)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != 99) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        if (resultCode == RESULT_OK) {
            weatherForecast = data?.getParcelableExtra<Parcelable>("WEATHER_FORECAST") as WeatherForecast
            getCurrentData()
        }
    }

    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        val call = retrofit.getCurrentWeather(weatherForecast.latWeather, weatherForecast.lonWeather, exclude, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    val currentData = response.body()!!
                    weatherForecast?.setCurrentWeather(currentData.current)
                    weatherForecast?.setDailyForecast(currentData.daily)
                    initView()
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        }
        )
    }

    private fun getCurrentCityName(latitude: Double, longitude: Double): String{
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].locality
    }

    companion object {
        var BaseUrl = "https://api.openweathermap.org"
        var AppId = "a568bd37f06c1bcd1ceeae9be4e359ee"
        var exclude = listOf("minutely", "hourly", "alerts")
    }
}
