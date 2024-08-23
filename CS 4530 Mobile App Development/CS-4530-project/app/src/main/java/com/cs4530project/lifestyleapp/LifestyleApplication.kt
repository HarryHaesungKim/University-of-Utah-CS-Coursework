package com.cs4530project.lifestyleapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LifestyleApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Inject scope and application context into singleton database
    val database by lazy{ LifestyleRoomDatabase.getDatabase(this, applicationScope) }

    val repository by lazy{ LifestyleRepository.getInstance(database.userDao(), applicationScope) }
}