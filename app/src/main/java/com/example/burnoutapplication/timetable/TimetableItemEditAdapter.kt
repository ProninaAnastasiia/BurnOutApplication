package com.example.burnoutapplication.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.burnoutapplication.databinding.TimetableItemCardEditBinding
import java.time.format.DateTimeFormatter

class TimetableItemEditAdapter (
    private val timetableItems: List<TimetableItem>, private val clickListener: TimetableCardClickListener
): RecyclerView.Adapter<TimetableItemEditAdapter.TimetableViewHolder>()
{
    class TimetableViewHolder(private val binding: TimetableItemCardEditBinding, private val clickListener: TimetableCardClickListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindTaskItem(timetableItem: TimetableItem)
        {
            binding.name.text = timetableItem.name
            binding.time.text = DateTimeFormatter.ofPattern("H:mm").format(timetableItem.startTime()) + " - " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.endTime())

            binding.editTimetableContainer.setOnClickListener{
                clickListener.editTimetableItem(timetableItem)
            }

            binding.moreBtn.setOnClickListener{
                clickListener.showPopup(binding.moreBtn, timetableItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TimetableItemCardEditBinding.inflate(from, parent, false)
        return TimetableViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        holder.bindTaskItem(timetableItems[position])
    }

    override fun getItemCount(): Int = timetableItems.size

}