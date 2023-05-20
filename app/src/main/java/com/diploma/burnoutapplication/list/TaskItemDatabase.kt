package com.diploma.burnoutapplication.list

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diploma.burnoutapplication.mood.MoodItem
import com.diploma.burnoutapplication.mood.MoodItemDao
import com.diploma.burnoutapplication.timetable.TimetableItem
import com.diploma.burnoutapplication.timetable.TimetableItemDao

// TODO: Решить что-то с базой данных, почему она лежит в папке списка дел и называется TaskItemDatabase, если в ней не только TaskItem`ы
@Database(entities = [TaskItem::class, TimetableItem::class, MoodItem::class], version = 1, exportSchema = false)
public abstract class TaskItemDatabase : RoomDatabase()
{
    abstract fun taskItemDao(): TaskItemDao
    abstract fun timetableItemDao(): TimetableItemDao
    abstract fun moodItemDao(): MoodItemDao

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