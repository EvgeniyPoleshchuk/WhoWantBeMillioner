package com.example.whowantbemillioner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.whowantbemillioner.ResulInfo

@Dao
interface UserDao {

    @Query("SELECT * FROM resulinfo")
    fun getAllUser(): List<ResulInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: ResulInfo)
}