package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import android.content.Intent


class SearchCity : AppCompatActivity(), SearchCityFragment.OnSomeEventListener {
    lateinit var weatherForecast: WeatherForecast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

        val searchCity: SearchCityFragment = SearchCityFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.searchCityFragment, searchCity)
    }

    override fun searchFinish(lat: Float?, lon: Float?){
        val intentResult = Intent()
        weatherForecast = WeatherForecast(lat, lon)
        intentResult.putExtra("WEATHER_FORECAST", weatherForecast)
        setResult(RESULT_OK, intentResult)
        finish()
    }

}