package com.diploma.burnoutapplication.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diploma.burnoutapplication.databinding.TimetableItemCardBinding
import java.time.format.DateTimeFormatter

class TimetableItemAdapter(private val timetableItems: List<TimetableItem>
): RecyclerView.Adapter<TimetableItemAdapter.TimetableViewHolder>()
{
    class TimetableViewHolder(private val binding: TimetableItemCardBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bindTaskItem(timetableItem: TimetableItem)
        {
            binding.name.text = timetableItem.name
            binding.time.text = DateTimeFormatter.ofPattern("H:mm").format(timetableItem.startTime()) + " - " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.endTime())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TimetableItemCardBinding.inflate(from, parent, false)
        return TimetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        holder.bindTaskItem(timetableItems[position])
    }

    override fun getItemCount(): Int = timetableItems.size

}