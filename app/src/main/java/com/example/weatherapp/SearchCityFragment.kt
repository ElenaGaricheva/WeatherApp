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

class SearchCityFragment : Fragment() {

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
        val bestCities: Array<String> =
            arrayOf("Lahore", "Berlin", "Lisbon", "Tokyo", "Toronto", "Sydney", "Osaka", "Istanbul")

        val citiesList: ListView = view.findViewById(R.id.citiesList)
        val searchCity: SearchView = view.findViewById(R.id.searchCity)
        val adapter: ArrayAdapter<String>? = activity?.let { ArrayAdapter<String>(it.baseContext, android.R.layout.simple_list_item_1, bestCities) }
        citiesList.adapter = adapter

        searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchString: String?): Boolean {
                if (bestCities.contains(searchString)) {
                    adapter?.filter?.filter(searchString)
                } else {
                    Toast.makeText(activity?.baseContext, "No match found", Toast.LENGTH_SHORT).show()
               }
                return false
            }
            override fun onQueryTextChange(searchString: String?): Boolean {
                adapter?.filter?.filter(searchString)
                return false
            }
        })
    }

    companion object {
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