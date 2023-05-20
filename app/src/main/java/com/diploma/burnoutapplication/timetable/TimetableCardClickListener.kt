package com.diploma.burnoutapplication.timetable

import android.widget.ImageButton

interface TimetableCardClickListener {
    fun editTimetableItem(timetableItem: TimetableItem)
    fun showPopup(show: ImageButton, timetableItem: TimetableItem)
}