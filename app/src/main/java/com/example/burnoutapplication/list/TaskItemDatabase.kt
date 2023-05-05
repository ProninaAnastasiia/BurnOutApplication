package com.example.burnoutapplication.list

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.burnoutapplication.timetable.TimetableItem
import com.example.burnoutapplication.timetable.TimetableItemDao

@Database(entities = [TaskItem::class, TimetableItem::class], version = 1, exportSchema = false)
public abstract class TaskItemDatabase : RoomDatabase()
{
    abstract fun taskItemDao(): TaskItemDao
    abstract fun timetableItemDao(): TimetableItemDao

    companion object
    {
        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        fun getDatabase(context: Context): TaskItemDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "task_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}