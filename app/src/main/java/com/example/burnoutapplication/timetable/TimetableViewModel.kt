package com.example.burnoutapplication.timetable

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TimetableViewModel(private val repository: TimetableItemRepository): ViewModel()
{
    var timetableItems: LiveData<MutableList<TimetableItem>> = repository.allTimetableItems.asLiveData()

    fun addTimetableItem(timetableItem: TimetableItem) = viewModelScope.launch {
        repository.insertTimetableItem(timetableItem)
    }

    fun updateTimetableItem(timetableItem: TimetableItem) = viewModelScope.launch {
        repository.updateTimetableItem(timetableItem)
    }

    fun deleteTimetableItem(timetableItem: TimetableItem) = viewModelScope.launch {
        repository.deleteTimetableItem(timetableItem)
    }

    fun copyTimetableItem(timetableItem: TimetableItem, dayOfWeek: String) = viewModelScope.launch {
        val newTimetable = TimetableItem(timetableItem.name, timetableItem.startTimeString, timetableItem.endTimeString, dayOfWeek)
        repository.insertTimetableItem(newTimetable)
    }

    fun copyTimetable(currentDay: String, dayToCopy: String) = viewModelScope.launch {
        repository.copyTimetable(currentDay, dayToCopy)
    }

    fun deleteTimetable(dayOfWeek: String) = viewModelScope.launch {
        repository.deleteTimetable(dayOfWeek)
    }

    class TimetableViewModelFactory(private val repository: TimetableItemRepository) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T
        {
            if (modelClass.isAssignableFrom(TimetableViewModel::class.java))
                return TimetableViewModel(repository) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}