package com.example.yohannsquizapp.database.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yohannsquizapp.model.Choice
import java.io.Serializable

@Entity
data class Question(val question: String?, var A: String?, var B: String?, var C: String?, var D: String?,var answer: String?) : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}