package com.example.yohannsquizapp.database.answer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.yohannsquizapp.database.question.Question
import java.io.Serializable
//
//@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = Question::class,
//            parentColumns = ["id"],
//            childColumns = ["questionId"],
//            onDelete = CASCADE
//        )
//    ]
//)
//data class Answer(val questionId: Int, val correctAns: String?): Serializable{
//
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0;
//}
