package com.diploma.burnoutapplication.list

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskViewModel(private val repository: TaskItemRepository) : ViewModel()
{
    val taskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()

    fun addTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(taskItem)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun deleteTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.isCompleted)
            taskItem.isCompleted = true
            taskItem.completedDateString = DateTimeFormatter.ISO_DATE.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }

    fun unCompleted(taskItem: TaskItem) = viewModelScope.launch {
        taskItem.isCompleted = false
        repository.updateTaskItem(taskItem)
    }

}

class TaskItemModelFactory(private val repository: TaskItemRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}