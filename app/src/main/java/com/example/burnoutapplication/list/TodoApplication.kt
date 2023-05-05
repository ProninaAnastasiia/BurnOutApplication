package com.example.burnoutapplication.list

import android.app.Application
import com.example.burnoutapplication.timetable.TimetableItemRepository

class TodoApplication : Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
    val repository2 by lazy { TimetableItemRepository(database.timetableItemDao()) }

}