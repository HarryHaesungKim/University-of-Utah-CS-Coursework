package com.cs4530project.lifestyleapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Insert replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userData: UserData)

    // Delete all
    @Query("DELETE FROM user_table")
    suspend fun delete()

    // Get the user info that is currently in the database
    // automatically triggered when the db is updated because of Flow<UserData>
    @Query("SELECT * from user_table LIMIT 1")
    fun getUser(): Flow<UserData>
}