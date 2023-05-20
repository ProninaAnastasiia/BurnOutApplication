package com.example.burnoutapplication.list

import androidx.recyclerview.widget.RecyclerView
import com.example.burnoutapplication.databinding.TaskItemCardBinding

class TaskItemViewHolder(
    private val binding: TaskItemCardBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bindTaskItem(taskItem: TaskItem)
    {
        binding.name.text = taskItem.name

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(binding.root.context))

        binding.completeButton.setOnClickListener{
            if(!taskItem.isCompleted) clickListener.completeTaskItem(taskItem)
            else clickListener.unCompleteTaskItem(taskItem)
        }

        binding.taskContainer.setOnLongClickListener{
            clickListener.editTaskItem(taskItem)
            false
        }

    }
}