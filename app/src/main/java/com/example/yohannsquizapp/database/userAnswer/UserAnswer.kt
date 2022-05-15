package com.example.yohannsquizapp.database.userAnswer

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class UserAnswer(val questionId: Int?, val answer: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}