package com.aneke.peter.eyweather.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.aneke.peter.eyweather.R
import com.aneke.peter.eyweather.databinding.DialogDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailDialogFragment : DialogFragment() {

    lateinit var binding: DialogDetailBinding
    private val viewModel : MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_detail, container, false) //DialogDetailBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.detailBackBtn.setOnClickListener { dialog?.dismiss() }

        viewModel.selectedData.observe(this, {
            it?.let { data ->
                val condition = data.weather.firstOrNull()?.main
                resolveAnim(condition)
            }
        })

    }


    private fun resolveAnim(condition: String?) {
        if (condition == null) {
            binding.conditionAnimation.setAnimation(R.raw.sun_cloudy)
        } else {
            if (condition.contains("clouds", true) || condition.contains("haze", true)) {
                binding.conditionAnimation.setAnimation(R.raw.light_clouds)
            }
            if (condition.contains("sun", true) || condition.contains("clear", true)) {
                binding.conditionAnimation.setAnimation(R.raw.sun)
            }
            if (condition.contains("rain", true)) {
                binding.conditionAnimation.setAnimation(R.raw.light_showers)
            }
            if (condition.contains("thunder", true)) {
                binding.conditionAnimation.setAnimation(R.raw.thunderstorm)
            }
        }
    }

}