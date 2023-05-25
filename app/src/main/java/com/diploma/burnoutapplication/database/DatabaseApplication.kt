package com.diploma.burnoutapplication.database

import android.app.Application
import com.diploma.burnoutapplication.list.TaskItemRepository
import com.diploma.burnoutapplication.mood.MoodItemRepository
import com.diploma.burnoutapplication.timetable.TimetableItemRepository

class DatabaseApplication : Application() {
    private val database by lazy { BurnoutAppDatabase.getDatabase(this) }
    val repositoryTask by lazy { TaskItemRepository(database.taskItemDao()) }
    val repositoryTimetable by lazy { TimetableItemRepository(database.timetableItemDao()) }
    val repositoryMood by lazy { MoodItemRepository(database.moodItemDao()) }
}