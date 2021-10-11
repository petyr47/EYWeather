package com.aneke.peter.eyweather.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneke.peter.eyweather.data.Resource
import com.aneke.peter.eyweather.network.model.NetworkResource
import com.aneke.peter.eyweather.network.model.WeatherApiResponse
import com.aneke.peter.eyweather.utils.resolveMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val weatherData = MutableLiveData<Resource<List<NetworkResource>>>()
    val selectedData = MutableLiveData<WeatherApiResponse>()
    val temp = Transformations.map(selectedData){ "${it.main.temp}°C"}
    val minTemp = Transformations.map(selectedData){"${it.main.temp_min}°C"}
    val maxTemp = Transformations.map(selectedData){"${it.main.temp_max}°C"}
    val humidity = Transformations.map(selectedData){"${it.main.humidity}%"}
    val coord = Transformations.map(selectedData){"${it.coord.lon} : ${it.coord.lat}"}
    val sunrise = Transformations.map(selectedData){ parseTime(it.sys.sunrise) }
    val sunset = Transformations.map(selectedData){parseTime(it.sys.sunset) }

    fun fetchWeatherData(){
        weatherData.value = Resource.loading()
        viewModelScope.launch {
            try {
                val result = repository.fetchWeatherData()
                Log.e("result ", result.toString())
                if (result.isNullOrEmpty()){
                    weatherData.postValue(Resource.error("Data not found"))
                } else {
                    weatherData.postValue(Resource.success(result, "success"))
                }
            } catch (e : Exception){
                e.printStackTrace()
                weatherData.postValue(Resource.error(e.resolveMessage()))
            }
        }
    }

    private fun parseTime(time : Int) : String {
        try {
            val outputFormat = SimpleDateFormat("HH:mm")
            val date = Date(time.toLong() * 1000)
            return outputFormat.format(date)
        } catch (e : Exception){
            e.printStackTrace()
            return ""
        }
    }
}