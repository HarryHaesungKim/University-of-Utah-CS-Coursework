package com.cs4530project.lifestyleapp

import kotlin.Throws
import org.json.JSONException
import org.json.JSONObject

object JSONWeatherUtils {
    @Throws(JSONException::class)
    fun getWeatherData(data: String?): WeatherData {
        val weatherData = WeatherData()

        //Start parsing JSON data
        val jsonObject = JSONObject(data!!) //Must throw JSONException
        val currentCondition = weatherData.currentCondition
        val jsonMain = jsonObject.getJSONObject("main")
        currentCondition.humidity = jsonMain.getInt("humidity").toDouble()
        currentCondition.pressure = jsonMain.getInt("pressure").toDouble()
        weatherData.currentCondition = currentCondition

        //Get the temperature, wind and cloud data.
        val temperature = weatherData.temperature
        val wind = weatherData.wind
        val clouds = weatherData.clouds
        temperature.maxTemp = jsonMain.getDouble("temp_max")
        temperature.minTemp = jsonMain.getDouble("temp_min")
        temperature.temp = jsonMain.getDouble("temp")
        weatherData.temperature = temperature
        return weatherData
    }
}

class WeatherData {
    //Setters and Getters
    var locationData: LocationData? = null
    var currentCondition = CurrentCondition()
    var temperature = Temperature()
    var wind = Wind()
    var rain = Rain()
    var snow = Snow()
    var clouds = Clouds()

    inner class CurrentCondition {
        var weatherId: Long = 0
        var condition: String? = null
        var descr: String? = null
        var icon: String? = null
        var pressure = 0.0
        var humidity = 0.0
    }

    inner class Temperature {
        var temp = 0.0
        var minTemp = 0.0
        var maxTemp = 0.0
    }

    inner class Wind {
        var speed = 0.0
        var deg = 0.0
    }

    inner class Rain {
        var time: String? = null
        var amount = 0.0
    }

    inner class Snow {
        var time: String? = null
        var amount = 0.0
    }

    inner class Clouds {
        var perc: Long = 0
    }
}

class LocationData {
    var latitude = 0.0
    var longitude = 0.0
    var country: String? = null
    var city: String? = null
    var sunset: Long = 0
    var sunrise: Long = 0
}