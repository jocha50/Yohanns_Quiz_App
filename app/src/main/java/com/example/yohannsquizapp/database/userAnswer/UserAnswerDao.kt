package com.example.yohannsquizapp.database.userAnswer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserAnswerDao {
    @Query("SELECT * FROM USERANSWER")
    suspend fun getAllUserAnswers(): List<UserAnswer>

    @Insert
    suspend fun addUserAnswer(userAnswer: UserAnswer)

    @Query("DELETE FROM USERANSWER")
    suspend fun deleteAllAnswers()
}