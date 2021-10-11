package com.aneke.peter.eyweather.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aneke.peter.eyweather.R
import com.aneke.peter.eyweather.databinding.ItemCityBinding
import com.aneke.peter.eyweather.network.model.WeatherApiResponse

class CityAdapter(val onItemClicked: (WeatherApiResponse) -> Unit) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    lateinit var list: List<WeatherApiResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
        holder.bindCity(list[position])

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    inner class CityViewHolder(val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindCity(weatherResponse: WeatherApiResponse) {

            val conditions = weatherResponse.weather.firstOrNull()?.main
            val desc = weatherResponse.weather.firstOrNull()?.description?.capitalize()
            val location = weatherResponse.name
            val temp = weatherResponse.main.temp
            resolveAnim(conditions)

            binding.rootCard.setOnClickListener {
                Log.e("adapter", "clicked")
                onItemClicked.invoke(weatherResponse)
            }

            binding.itemCity.text = location
            binding.itemConditions.text = conditions
            binding.itemConditionsDesc.text = desc
            binding.itemTemp.text = "Temp: $temp°C"
        }

        private fun resolveAnim(condition: String?) {
            if (condition == null) {
                binding.itemAnimation.setAnimation(R.raw.sun_cloudy)
            } else {
                if (condition.contains("clouds", true) || condition.contains("haze", true)) {
                    binding.itemAnimation.setAnimation(R.raw.light_clouds)
                }
                if (condition.contains("sun", true) || condition.contains("clear", true)) {
                    binding.itemAnimation.setAnimation(R.raw.sun)
                }
                if (condition.contains("rain", true) || condition.contains("mist", true)) {
                    binding.itemAnimation.setAnimation(R.raw.light_showers)
                }
                if (condition.contains("thunder", true)) {
                    binding.itemAnimation.setAnimation(R.raw.thunderstorm)
                }
            }
        }

    }


}