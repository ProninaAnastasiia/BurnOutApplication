package com.example.burnoutapplication.timetable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "timetable_item_table")
class TimetableItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "startTimeString") var startTimeString: String?,
    @ColumnInfo(name = "endTimeString") var endTimeString: String?,
    @ColumnInfo(name = "dayOfWeekString") var dayOfWeekString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0)
{
    fun startTime(): LocalTime? = if (startTimeString == null) null else LocalTime.parse(startTimeString, timeFormatter)
    fun endTime(): LocalTime? = if (endTimeString == null) null else LocalTime.parse(endTimeString, timeFormatter)
    fun dayOfWeek(): DayOfWeek? = if (dayOfWeekString == null) null else DayOfWeek.from(dayFormatter.parse(dayOfWeekString))

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE")
    }
}