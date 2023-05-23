package com.diploma.burnoutapplication.list

import android.app.Application
import com.diploma.burnoutapplication.mood.MoodItemRepository
import com.diploma.burnoutapplication.timetable.TimetableItemRepository

class TodoApplication : Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repositoryTask by lazy { TaskItemRepository(database.taskItemDao()) }
    val repositoryTimetable by lazy { TimetableItemRepository(database.timetableItemDao()) }
    val repositoryMood by lazy { MoodItemRepository(database.moodItemDao()) }
}