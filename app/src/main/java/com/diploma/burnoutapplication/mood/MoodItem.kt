package com.diploma.burnoutapplication.mood

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diploma.burnoutapplication.R

enum class Mood(val value: Int, val rus: String){
    Unbearable(1,"Невыносимо"), Angry(5,"Разжраженно"), Anxious(3,"Тревожно"), Guilty(4,"Виновато"),
    Tired(2,"Апатично, устало"), NotBad(6,"Удовлетворительно"), Calm(7,"Спокойно"), Fine(8,"Всё в порядке")
}

@Entity(tableName = "mood_item_table")
class MoodItem(@ColumnInfo(name = "name") var name: String,
               @ColumnInfo(name = "mark") var mark: Int,
               @ColumnInfo(name = "dateAddedString") var dateAddedString: String,
               @ColumnInfo(name = "timeAddedString") var timeAddedString: String,
               @PrimaryKey(autoGenerate = true) var id: Int = 0)
{
     fun imageResource(): Int {
        return when(name){
            "Всё в порядке" -> R.drawable.emoji_fine
            "Удовлетворительно" -> R.drawable.emoji_not_bad
            "Апатично, устало" -> R.drawable.emoji_tired
            "Невыносимо" -> R.drawable.emoji_unbearable
            "Виновато" -> R.drawable.emoji_guilty
            "Тревожно" -> R.drawable.emoji_anxious
            "Спокойно" -> R.drawable.emoji_calm
            else -> R.drawable.emoji_angry
        }
    }
}