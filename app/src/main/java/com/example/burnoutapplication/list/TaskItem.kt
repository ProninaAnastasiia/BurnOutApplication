package com.example.burnoutapplication.list

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.burnoutapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "task_item_table")
class TaskItem(@ColumnInfo(name = "name") var name: String,
               @ColumnInfo(name = "completedDateString") var completedDateString: String?,
               @PrimaryKey(autoGenerate = true) var id: Int = 0)
{
    private fun completedDate(): LocalDate? = if (completedDateString == null) null else LocalDate.parse(completedDateString, DateTimeFormatter.ISO_DATE)

    fun isCompleted() = completedDate() != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.ic_checked_24 else R.drawable.ic_unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.teal_200)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}