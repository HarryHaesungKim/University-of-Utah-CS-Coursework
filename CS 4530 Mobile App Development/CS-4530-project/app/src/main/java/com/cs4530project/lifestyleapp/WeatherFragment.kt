package com.cs4530project.lifestyleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

class WeatherFragment : Fragment() {

    private var mTvTemp: TextView? = null
    private var mTvPress: TextView? = null
    private var mTvHum: TextView? = null
    private var mTvCloud: TextView? = null
    private var mTvWind: TextView? = null

    private val mLifestyleViewModel: LifestyleViewModel by activityViewModels()

    private val observer: Observer<WeatherData?> =
        Observer { weatherData ->
            if (weatherData != null) {
                mTvTemp!!.text = "Temperature\n" + Math.round(weatherData.temperature.temp - 273.15) + "\u2103"
                mTvHum!!.text = "Humidity\n" + weatherData.currentCondition.humidity + "%"
                mTvPress!!.text = "Pressure\n" + weatherData.currentCondition.pressure + " hPa"
                mTvCloud!!.text = "Overcast\n" + weatherData.clouds.perc + "%"
                mTvWind!!.text = "Wind\n" + weatherData.wind.speed + " mph"
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        mTvTemp = view.findViewById<View>(R.id.tv_temp) as TextView
        mTvPress = view.findViewById<View>(R.id.tv_pressure) as TextView
        mTvHum = view.findViewById<View>(R.id.tv_humidity) as TextView
        mTvCloud = view.findViewById<View>(R.id.tv_cloud) as TextView
        mTvWind = view.findViewById<View>(R.id.tv_wind) as TextView

        mLifestyleViewModel.weather.observe(viewLifecycleOwner, observer)

        return view
    }
}