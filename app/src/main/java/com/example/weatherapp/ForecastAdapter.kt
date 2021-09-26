package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForecastAdapter(val dailyForecast: List<Daily>) :
    RecyclerView.Adapter<ForecastAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var date: TextView? = null
        var temperature: TextView? = null

        init{
            date = itemView.findViewById(R.id.date)
            temperature = itemView.findViewById(R.id.temperature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val oneDayItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_forecast, parent, false)
        return MyViewHolder(oneDayItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return dailyForecast.size
    }
}