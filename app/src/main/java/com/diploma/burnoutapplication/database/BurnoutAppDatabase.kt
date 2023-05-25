package com.diploma.burnoutapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diploma.burnoutapplication.list.TaskItem
import com.diploma.burnoutapplication.list.TaskItemDao
import com.diploma.burnoutapplication.mood.MoodItem
import com.diploma.burnoutapplication.mood.MoodItemDao
import com.diploma.burnoutapplication.timetable.TimetableItem
import com.diploma.burnoutapplication.timetable.TimetableItemDao

@Database(entities = [TaskItem::class, TimetableItem::class, MoodItem::class], version = 1, exportSchema = false)
public abstract class BurnoutAppDatabase : RoomDatabase()
{
    abstract fun taskItemDao(): TaskItemDao
    abstract fun timetableItemDao(): TimetableItemDao
    abstract fun moodItemDao(): MoodItemDao

    companion object
    {
        @Volatile
        private var INSTANCE: BurnoutAppDatabase? = null

        fun getDatabase(context: Context): BurnoutAppDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BurnoutAppDatabase::class.java,
                    "burnout_app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}