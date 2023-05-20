package com.diploma.burnoutapplication.mood

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diploma.burnoutapplication.databinding.MoodItemCardBinding

class MoodItemAdapter(private val moodItems: List<MoodItem>, private val clickListener: MoodItemClickListener
): RecyclerView.Adapter<MoodItemAdapter.MoodViewHolder>()
{
    private lateinit var context: Context

    class MoodViewHolder(private val binding: MoodItemCardBinding, private val clickListener: MoodItemClickListener): RecyclerView.ViewHolder(binding.root)
    {
        fun bindMoodItem(moodItem: MoodItem)
        {
            binding.imageView.setImageResource(moodItem.imageResource())
            binding.name.text = moodItem.name
            binding.time.text = "${moodItem.dateAddedString.dropLast(5)} ${moodItem.timeAddedString}"

            binding.moodContainer.setOnLongClickListener{
                clickListener.deleteMoodItem(moodItem)
                false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        context = parent.context
        val from = LayoutInflater.from(parent.context)
        val binding = MoodItemCardBinding.inflate(from, parent, false)
        return MoodViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        holder.bindMoodItem(moodItems[position])
    }

    override fun getItemCount(): Int = moodItems.size

}