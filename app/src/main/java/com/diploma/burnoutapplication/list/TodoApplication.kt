package com.diploma.burnoutapplication.list

import android.app.Application
import com.diploma.burnoutapplication.mood.MoodItemRepository
import com.diploma.burnoutapplication.timetable.TimetableItemRepository

class TodoApplication : Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
    val repository2 by lazy { TimetableItemRepository(database.timetableItemDao()) }
    val repository3 by lazy { MoodItemRepository(database.moodItemDao()) }
}