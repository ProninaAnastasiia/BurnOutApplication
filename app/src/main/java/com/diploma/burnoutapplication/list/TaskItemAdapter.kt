package com.diploma.burnoutapplication.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diploma.burnoutapplication.databinding.TaskItemCardBinding

class TaskItemAdapter( private val taskItems: List<TaskItem>,
                       private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = TaskItemCardBinding.inflate(itemView, parent, false)
        return TaskItemViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    override fun getItemCount(): Int = taskItems.size
}