package com.example.yohannsquizapp.database.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {

    @Insert
    suspend fun addQuestion(question: Question)

    @Insert
    suspend fun addMultipleQuestions(question: List<Question>);

    @Query( "SELECT * FROM QUESTION ORDER BY ID DESC")
    suspend fun getAllQuestions():List<Question>

    @Query("DELETE FROM QUESTION")
    suspend fun deleteAllQuestions()
}