package com.example.whowantbemillioner.data

import android.content.Context
import androidx.room.Room
import com.example.whowantbemillioner.ResulInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionsRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-user"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val userDao = db.userDao()

    suspend fun getAllUsersFromCache(): List<ResulInfo> {
        return withContext(ioDispatcher) {
            userDao.getAllUser()
        }
    }

    suspend fun insertUserIntoCache(user: ResulInfo) {
        withContext(ioDispatcher) {
            userDao.insertUser(user)
        }
    }
}