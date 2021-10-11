package com.aneke.peter.eyweather.main

import android.os.Bundle
import android.util.Log
import com.aneke.peter.eyweather.data.Status
import com.aneke.peter.eyweather.databinding.ActivityMainBinding
import com.aneke.peter.eyweather.network.model.WeatherApiResponse
import com.aneke.peter.eyweather.utils.LoadableActivity
import com.aneke.peter.eyweather.utils.showAlert
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : LoadableActivity() {

    private val viewModel : MainViewModel by viewModel()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchWeatherData()
        val adapter = CityAdapter { data ->
            Log.e("activity", "clicked")
            viewModel.selectedData.value = data
            val dialog = DetailDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
        }
        adapter.list = emptyList()
        binding.weatherList.adapter = adapter

        viewModel.weatherData.observe(this, {
            it?.let { resource ->
                when(resource.status){
                    Status.ERROR -> {
                        hideLoader()
                        showAlert(message = resource.resolveMessage())
                    }
                    Status.LOADING -> {
                        showLoader()
                    }
                    Status.SUCCESS -> {
                        hideLoader()
                        val data = resource.data?.map { it.data as WeatherApiResponse } ?: emptyList()
                        adapter.list = data
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        })

    }
}