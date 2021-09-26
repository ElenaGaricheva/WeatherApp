package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class ForecastAdapter(private val dailyForecast: List<Daily>) :
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
        val date = dailyForecast.get(position).dt.toLong().let { convertDate(it) }
        val temp = dailyForecast.get(position).temp?.let { getTempForecast(it) }

        holder.date?.text = date
        holder.temperature?.text = temp

    }

    override fun getItemCount(): Int {
        return dailyForecast.size
    }

    private fun getTempForecast(temp: Temp): String {
        val iconCelsius = "\u00B0"
        val minTemp = temp.min?.let { convertKelvinsToCelsius(it) }
        val maxTemp = temp.max?.let { convertKelvinsToCelsius(it) }
        return maxTemp.toString() + iconCelsius + " / " + minTemp.toString() + iconCelsius
    }

    private fun convertKelvinsToCelsius(temp: Float): Int{
        val kelvinsTemp: Float = 273.15F
        return temp.minus(kelvinsTemp).toInt()
    }

    private fun convertDate(date: Long): String {
        val localDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault())
        val dayOfWeek = localDate.dayOfWeek
        val dayOfMonth = localDate.dayOfMonth
        val month = localDate.month
        return dayOfWeek.toString().substring(0, 3) + ", " +
                dayOfMonth.toString() + " " + month.toString().substring(0, 3)
    }
}