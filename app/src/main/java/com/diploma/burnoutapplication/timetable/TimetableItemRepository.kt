package com.diploma.burnoutapplication.timetable

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TimetableItemRepository(private val timetableItemDao: TimetableItemDao) {
    val allTimetableItems: Flow<MutableList<TimetableItem>> = timetableItemDao.allTimetableItems()

    @WorkerThread
    suspend fun insertTimetableItem(timetableItem: TimetableItem)
    {
        timetableItemDao.insertTimetableItem(timetableItem)
    }

    @WorkerThread
    suspend fun updateTimetableItem(timetableItem: TimetableItem)
    {
        timetableItemDao.updateTimetableItem(timetableItem)
    }

    @WorkerThread
    suspend fun deleteTimetableItem(timetableItem: TimetableItem)
    {
        timetableItemDao.deleteTimetableItem(timetableItem)
    }

    @WorkerThread
    suspend fun deleteTimetable(dayOfWeek: String)
    {
        timetableItemDao.deleteTimetable(dayOfWeek)
    }

    @WorkerThread
    suspend fun copyTimetable(currentDay: String, dayToCopy: String)
    {
        timetableItemDao.copyTimetableItemsByDay(currentDay, dayToCopy)
    }

}