package com.example.burnoutapplication.list

interface TaskItemClickListener {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun unCompleteTaskItem(taskItem: TaskItem)
}