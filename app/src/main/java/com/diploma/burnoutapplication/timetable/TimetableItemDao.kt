package com.diploma.burnoutapplication.timetable

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableItemDao {
    @Query("SELECT * FROM timetable_item_table")
    fun allTimetableItems(): Flow<MutableList<TimetableItem>>

    @Query("INSERT INTO timetable_item_table (name, startTimeString, endTimeString, dayOfWeekString) SELECT name, startTimeString, endTimeString, :dayToCopy FROM timetable_item_table WHERE dayOfWeekString=:currentDay")
    suspend fun copyTimetableItemsByDay(currentDay: String, dayToCopy: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableItem(timetableItem: TimetableItem)

    @Update
    suspend fun updateTimetableItem(timetableItem: TimetableItem)

    @Delete
    suspend fun deleteTimetableItem(timetableItem: TimetableItem)

    @Query("DELETE FROM timetable_item_table WHERE dayOfWeekString=:dayOfWeek")
    suspend fun deleteTimetable(dayOfWeek: String)
}