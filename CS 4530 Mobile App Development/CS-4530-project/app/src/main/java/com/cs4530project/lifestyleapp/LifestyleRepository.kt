package com.cs4530project.lifestyleapp

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LifestyleRepository private constructor(userDao: UserDao){

    val calorieData = MutableLiveData<CalorieData>()
    val weatherData = MutableLiveData<WeatherData>()
    val hikeData = MutableLiveData<List<MapsData>>()
    val userData: Flow<UserData> = userDao.getUser()

    private var mUserDao: UserDao = userDao
    private var mJsonWeatherString: String? = null
    private var mJsonHikeString: String? = null

    fun setUserData(newUserData: UserData) {
        mScope.launch(Dispatchers.IO) {

            // Insert user data into database. This will trigger a flow.
            // All db ops should happen on a background thread.
            insert(newUserData)
        }
    }

    fun refreshRepo(userData: UserData) {
        mScope.launch(Dispatchers.IO) {

            // compute new calorie data
            val bmr = calculateBMR(userData)
            val dtci = calculateDTCI(userData)
            calorieData.postValue(CalorieData(bmr, dtci))

            // fetch weather data on a worker thread
            fetchAndParseWeatherData(userData.latitude, userData.longitude)

            // after the suspend function returns, update the view
            if (mJsonWeatherString != null) {
                weatherData.postValue(JSONWeatherUtils.getWeatherData(mJsonWeatherString))
            }
        }
    }

    fun getHikes(userData: UserData) {
        mScope.launch(Dispatchers.IO) {

            // fetch hike data on a worker thread
            fetchHikeData(userData.latitude, userData.longitude)

            // after the suspend function returns, update the view
            if (mJsonHikeString != null) {
                hikeData.postValue(JSONMapUtils.getMapsData(mJsonHikeString))
            }
        }
    }

    @WorkerThread
    suspend fun fetchHikeData(latitude: Double, longitude: Double) {
        val hikeDataURL = NetworkUtils.buildURLFromStringMaps(latitude, longitude)
        if (hikeDataURL != null) {
            val jsonHikeData = NetworkUtils.getDataFromURLMaps(hikeDataURL)
            if (jsonHikeData != null) {
                mJsonHikeString = jsonHikeData
            }
        }
    }

    @WorkerThread
    suspend fun fetchAndParseWeatherData(latitude: Double, longitude: Double) {
        val weatherDataURL = NetworkUtils.buildURLFromStringWeather(latitude, longitude)
        if (weatherDataURL != null) {
            // This is actually a blocking call unless you're using an
            // asynchronous IO library (which we're not). However, it is a blocking
            // call on a background thread, not on the UI thread
            val jsonWeatherData = NetworkUtils.getDataFromURLWeather(weatherDataURL)
            if (jsonWeatherData != null) {
                mJsonWeatherString = jsonWeatherData
            }
        }
    }

    @WorkerThread
    suspend fun insert(newUserData: UserData) {
        mUserDao.insert(newUserData)
    }

    // make the repository singleton
    companion object {
        private var mInstance: LifestyleRepository? = null
        private lateinit var mScope: CoroutineScope
        @Synchronized
        fun getInstance(userDao: UserDao, scope: CoroutineScope): LifestyleRepository {
            mScope = scope
            return mInstance?: synchronized(this){
                val instance = LifestyleRepository(userDao)
                mInstance = instance
                instance
            }
        }
    }

    // Calculate basal metabolic rate
    private fun calculateBMR(userData: UserData): Double {
        val weightKg = userData.weight.toDouble().times(0.453592)
        val ftToCm = userData.feet.toDouble().times(30.48)
        val inToCm = userData.inches.toDouble().times(2.54)
        val years = userData.age.toDouble()
        return when (userData.sex) {
            "male" ->
                88.362 +(13.397 * weightKg) + (4.799 * (ftToCm + inToCm)) - (5.677 * years)
            else ->
                447.593 + (9.247 * weightKg) + (3.098 * (ftToCm + inToCm)) - (4.330 * years)
        }
    }

    // Calculate daily target caloric intake
    // sedentary (little or no exercise): AMR = BMR x 1.2
    // lightly active (exercise 1–3 days/week): AMR = BMR x 1.375
    // moderately active (exercise 3–5 days/week): AMR = BMR x 1.55
    // very active (hard exercise 6–7 days/week): AMR = BMR x 1.9
    private fun calculateDTCI(userData: UserData): Double{
        val bmr = calculateBMR(userData)
        return when (userData.activityLevel) {
            "very active" -> bmr * 1.9
            "moderately active" -> bmr * 1.55
            "lightly active" -> bmr * 1.357
            else -> bmr * 1.2
        }
    }
}