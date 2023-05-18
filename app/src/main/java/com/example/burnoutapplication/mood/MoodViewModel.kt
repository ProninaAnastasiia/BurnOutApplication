package com.example.burnoutapplication.mood

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MoodViewModel (private val repository: MoodItemRepository) : ViewModel()
{
    val moodItems: LiveData<List<MoodItem>> = repository.allMoodItems.asLiveData()

    fun addMoodItem(moodItem: MoodItem) = viewModelScope.launch {
        repository.insertMoodItem(moodItem)
    }

    fun deleteMoodItem(moodItem: MoodItem) = viewModelScope.launch {
        repository.deleteMoodItem(moodItem)
    }



}

class MoodItemModelFactory(private val repository: MoodItemRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(MoodViewModel::class.java))
            return MoodViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}