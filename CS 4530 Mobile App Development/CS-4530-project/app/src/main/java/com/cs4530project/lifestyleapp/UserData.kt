package com.cs4530project.lifestyleapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserData {
    @PrimaryKey
    var id = 0
    var name: String? = ""
    var city: String? = ""
    var country: String? = ""
    var age = 18
    var feet = 5
    var inches = 6
    var weight = 200
    var sex = "male"
    var activityLevel = "very active"
    var imageFilePath: String? = null
    var latitude = 0.0
    var longitude = 0.0
}