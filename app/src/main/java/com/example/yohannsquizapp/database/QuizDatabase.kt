package com.example.yohannsquizapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yohannsquizapp.database.question.Question
import com.example.yohannsquizapp.database.question.QuestionDao
import com.example.yohannsquizapp.database.userAnswer.UserAnswer
import com.example.yohannsquizapp.database.userAnswer.UserAnswerDao

@Database(
    entities = [Question::class, UserAnswer::class],
    version = 4,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//
//    ]
)

abstract class QuizDatabase() : RoomDatabase() {

    abstract fun getQuestionDao(): QuestionDao
    abstract fun getUserAnswerDao(): UserAnswerDao

    companion object {
        @Volatile
        private var instance: QuizDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            QuizDatabase::class.java,
            "quizdatabase"
        ).fallbackToDestructiveMigration()
            .build()

    }
}