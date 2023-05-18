package com.example.burnoutapplication.mood

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MoodItemRepository (private val moodItemDao: MoodItemDao) {
    val allMoodItems: Flow<List<MoodItem>> = moodItemDao.allMoodItems()

    @WorkerThread
    suspend fun insertMoodItem(moodItem: MoodItem)
    {
        moodItemDao.insertMoodItem(moodItem)
    }

    @WorkerThread
    suspend fun deleteMoodItem(moodItem: MoodItem)
    {
        moodItemDao.deleteMoodItem(moodItem)
    }
}