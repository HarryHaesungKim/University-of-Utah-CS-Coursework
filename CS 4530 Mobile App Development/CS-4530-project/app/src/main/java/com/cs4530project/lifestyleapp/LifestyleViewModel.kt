package com.cs4530project.lifestyleapp

import androidx.lifecycle.*

class LifestyleViewModel(repository: LifestyleRepository) : ViewModel() {

    private val calorieData: LiveData<CalorieData> = repository.calorieData
    private val weatherData: LiveData<WeatherData> = repository.weatherData
    private val hikeData: LiveData<List<MapsData>> = repository.hikeData
    private val userData: LiveData<UserData> = repository.userData.asLiveData()

    private var mLifestyleRepository: LifestyleRepository = repository

    fun setUserData(newUserData: UserData) {
        mLifestyleRepository.setUserData(newUserData)
    }

    fun refreshData() {
        if (user.value != null)
            mLifestyleRepository.refreshRepo(user.value!!)
    }

    fun getHikes() {
        mLifestyleRepository.getHikes(user.value!!)
    }

    val user: LiveData<UserData>
        get() = userData

    val calories: LiveData<CalorieData>
        get() = calorieData

    val weather: LiveData<WeatherData>
        get() = weatherData

    val hikes: LiveData<List<MapsData>>
        get() = hikeData
}

// This factory class allows us to define custom constructors for the view model
class LifestyleViewModelFactory(private val repository: LifestyleRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LifestyleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LifestyleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}