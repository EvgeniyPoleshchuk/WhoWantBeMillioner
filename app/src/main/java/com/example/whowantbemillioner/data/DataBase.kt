package com.example.whowantbemillioner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whowantbemillioner.ResulInfo

@Database(entities = [ResulInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}

