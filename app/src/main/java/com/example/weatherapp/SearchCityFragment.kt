package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchCityFragment : Fragment() {

    private var citiesList: ArrayList<CitiesResponse>? = arrayListOf()
    lateinit var citiesListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val searchCity: SearchView = view.findViewById(R.id.searchCity)
        citiesListView = view.findViewById(R.id.citiesList)


        searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchString: String?): Boolean {
                getCurrentData(searchString)
                return false
            }
            override fun onQueryTextChange(searchString: String?): Boolean {
                return false
            }
        })

/*        citiesListView.setOnClickListener{

        }*/
    }

    private fun getCurrentData(cityName: String?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        val call = retrofit.getCitiesList(cityName, limit, AppId)
        call.enqueue(object : Callback<ArrayList<CitiesResponse>> {
            override fun onResponse(
                call: Call<ArrayList<CitiesResponse>>,
                response: Response<ArrayList<CitiesResponse>>
            ) {
                if (response.code() == 200) {
                    val citiesInfo = response.body()!!
                    citiesList = citiesInfo
//
                    if (citiesList?.size != 0) {
                        val citiesArray: ArrayList<String> = arrayListOf()
                        for (city in citiesList!!) {
                            city.localNames?.get("ru")?.let { citiesArray.add(it) }
                        }
                        val adapter: ArrayAdapter<String>? = activity?.let { ArrayAdapter<String>(it.baseContext, android.R.layout.simple_list_item_1, citiesArray) }
                        citiesListView.adapter = adapter
                    } else {
                        Toast.makeText(activity?.baseContext, "No match found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //
            override fun onFailure(call: Call<ArrayList<CitiesResponse>>, t: Throwable) {
            }
        }
        )
    }

    companion object {
        const val BaseUrl = "https://api.openweathermap.org"
        const val AppId = "a568bd37f06c1bcd1ceeae9be4e359ee"
        const val limit: Int = 1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchCityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchCityFragment().apply {
                arguments = Bundle().apply {
/*                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)*/
                }
            }
    }
}