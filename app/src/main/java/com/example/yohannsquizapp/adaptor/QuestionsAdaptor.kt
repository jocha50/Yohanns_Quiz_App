package com.example.yohannsquizapp.adaptor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yohannsquizapp.R
import com.example.yohannsquizapp.database.question.Question
import com.example.yohannsquizapp.database.userAnswer.UserAnswer
import kotlinx.android.synthetic.main.fragment_home.view.question
import kotlinx.android.synthetic.main.question_layout.view.*

class QuestionsAdaptor(
    private val questions: List<Question>,
    private val userAnswerMap: HashMap<Int?, UserAnswer>
) :
    RecyclerView.Adapter<QuestionsAdaptor.QuestionViewHolder>() {



    class QuestionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {

        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        holder.view.question.text = questions[position].question
        holder.view.correctAnswer.text = questions[position].answer.toString()
        holder.view.userAnswer.text = userAnswerMap.get(questions[position].id)?.answer
        if(questions[position].answer.toString().trim() != userAnswerMap.get(questions[position].id)?.answer.toString().trim()){
            holder.view.userAnswer.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int = questions.size

    private fun getUserAnswer(questions: HashMap<Int,Question>,  userAnswerMap: HashMap<Int?, UserAnswer>){

    }

}