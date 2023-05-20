package com.diploma.burnoutapplication.mood

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodItemDao {
    @Query("SELECT * FROM mood_item_table ORDER BY id ASC")
    fun allMoodItems(): Flow<List<MoodItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodItem(moodItem: MoodItem)

    @Delete
    suspend fun deleteMoodItem(moodItem: MoodItem)
}