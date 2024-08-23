package com.cs4530project.lifestyleapp

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.Throws

object NetworkUtils {

    // Hikes values: (Api key from Huy)
    private const val BASE_URL_MAPS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
    private const val RADIUS_QUERY = "&radius="
    private const val radius = 5000
    private const val TYPE_QUERY = "&type=point_of_interest"
    private const val KEYWORD_QUERY = "&keyword="
    private const val API_QUERY_MAPS = "&key="
    private const val API_MAPS_KEY = "AIzaSyAsTb3A7R4a7v93CPoBLPTrDGCtfZ1ZQyk"

    // Weather values: (Api key from Tarik)
    private const val BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/weather?lat="
    private const val LNG_URL_WEATHER = "&lon="
    private const val API_QUERY_WEATHER = "&appid="
    private const val API_WEATHER_KEY = "9c5d27029e2b42b485004e850ef5c96a"

    @JvmStatic
    fun buildURLFromStringMaps(lat: Double, long: Double): URL? {
        var myURL: URL? = null
        try {
            myURL = URL(BASE_URL_MAPS + lat + "," + long
                    + RADIUS_QUERY + radius
                    + TYPE_QUERY + KEYWORD_QUERY + "hiking+trails"
                    + API_QUERY_MAPS + API_MAPS_KEY)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return myURL
    }

    @JvmStatic
    @Throws(IOException::class)
    fun getDataFromURLMaps(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        return try {
            val inputStream = urlConnection.inputStream

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            val hasInput = scanner.hasNext()
            if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }


    // ---- Weather Logic ----
    @JvmStatic
    fun buildURLFromStringWeather(lat: Double, long: Double): URL? {
        var myURL: URL? = null
        try {
            myURL = URL(BASE_URL_WEATHER + lat + LNG_URL_WEATHER
                    + long + API_QUERY_WEATHER + API_WEATHER_KEY)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return myURL
    }

    @JvmStatic
    @Throws(IOException::class)
    fun getDataFromURLWeather(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        return try {
            val inputStream = urlConnection.inputStream

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            val hasInput = scanner.hasNext()
            if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}
