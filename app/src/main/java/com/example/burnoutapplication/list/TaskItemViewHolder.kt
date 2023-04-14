package com.example.burnoutapplication.list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.burnoutapplication.databinding.TaskItemCardBinding

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCardBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bindTaskItem(taskItem: TaskItem)
    {
        binding.name.text = taskItem.name

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener{
            if(!taskItem.isCompleted()) clickListener.completeTaskItem(taskItem)
            else clickListener.unCompleteTaskItem(taskItem)
        }

        binding.taskContainer.setOnLongClickListener{
            clickListener.editTaskItem(taskItem)
            false
        }

    }
}