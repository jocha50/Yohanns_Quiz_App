package com.example.yohannsquizapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yohannsquizapp.adaptor.QuestionsAdaptor
import com.example.yohannsquizapp.database.QuizDatabase
import com.example.yohannsquizapp.database.question.Question
import com.example.yohannsquizapp.database.userAnswer.UserAnswer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : BaseFragment() {

    private lateinit var userAnswers: List<UserAnswer>
    private lateinit var questionsFromDatabase: List<Question>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val recyclerViewQuestions = view.findViewById<RecyclerView>(R.id.recycler_view_questions)


        recyclerViewQuestions.setHasFixedSize(true)
        recyclerViewQuestions.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)


        launch {
            context?.let {
                userAnswers = QuizDatabase(it).getUserAnswerDao().getAllUserAnswers()
                questionsFromDatabase = QuizDatabase(it).getQuestionDao().getAllQuestions()
                println("all user answers $userAnswers")
                val userAnswerMap = HashMap<Int?,UserAnswer>()

                for (ans in userAnswers){
                    userAnswerMap.put(ans.questionId,ans)
                }
                recyclerViewQuestions.adapter = QuestionsAdaptor(questionsFromDatabase, userAnswerMap)
            }
        }
        val backButton = view.findViewById<FloatingActionButton>(R.id.backBtn)

        backButton.setOnClickListener{
            val intent = Intent(context, HomeFragment::class.java)
            startActivity(intent)
        }

        return view
    }


}