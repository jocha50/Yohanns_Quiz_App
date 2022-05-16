package com.example.yohannsquizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yohannsquizapp.database.QuizDatabase
import com.example.yohannsquizapp.database.question.Question
import com.example.yohannsquizapp.database.question.QuestionDao
import com.example.yohannsquizapp.database.userAnswer.UserAnswer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment() {

    private var index = 0
    private lateinit var questionsFromDatabase: List<Question>
    private var selectedAnswer: String = ""
    private var score: Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val nextButton = view.findViewById<Button>(R.id.nextBtn)
        val submitButton = view.findViewById<Button>(R.id.submitBtn)

        launch {
            context?.let { it ->
//                QuizDatabase(it).getQuestionDao().deleteAllQuestions();
                QuizDatabase(it).getUserAnswerDao().deleteAllAnswers()
                questionsFromDatabase = QuizDatabase(it).getQuestionDao().getAllQuestions()

                if (questionsFromDatabase.size == 0) {
                    QuizDatabase(it).getQuestionDao()
                        .addMultipleQuestions(InsertQuestionsIntoDatabase())
                    questionsFromDatabase = QuizDatabase(it).getQuestionDao().getAllQuestions()
                }



                setQuestionOnScreen(view)
                val questionMap: HashMap<Int, Question> = HashMap()

                for (q in questionsFromDatabase!!) {
                    questionMap.put(q.id, q)
                }
//                var userAnswer = QuizDatabase(it).getUserAnswerDao().getAllUserAnswers()
//                println("user answer $userAnswer")
            }
        }

        nextButton.setOnClickListener {
            println("inside next btn $selectedAnswer and ---> ${questionsFromDatabase.get(index).id}")
            if (selectedAnswer != "") {

                launch {
                    context?.let {
                        val userAnswer =
                            UserAnswer(questionsFromDatabase?.get(index)?.id, selectedAnswer)
                        if (questionsFromDatabase.get(index).answer == selectedAnswer) {
                            score++
                        }
                        println("user answer to be saved $userAnswer ---> index $index")
                        QuizDatabase(it).getUserAnswerDao().addUserAnswer(userAnswer)

                        index++
                        setQuestionOnScreen(view)
                        radioGroup.clearCheck()

                    }
                }
            } else {
                Toast.makeText(it.context, "please choose an answer", Toast.LENGTH_LONG).show()
            }

        }

        submitButton.setOnClickListener {
            launch {
                context?.let {
                    val questionMap: HashMap<Int, Question> = HashMap()
                    val userAnswerMap = HashMap<Int?, UserAnswer>()

                    val userAnswers = QuizDatabase(it).getUserAnswerDao().getAllUserAnswers()
                    for (ans in userAnswers) {
                        userAnswerMap.put(ans.questionId, ans)
                    }

                }
            }

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Review Questions",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK button
                            val action = HomeFragmentDirections.actionHomeFragmentToResultFragment()
                            Navigation.findNavController(view).navigate(action)

                        })
                    setNegativeButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                }

                builder.setTitle("Quiz Result")
                builder.setMessage("Your Score is $score / 15")

                // Create the AlertDialog
                builder.create()
            }

            if (alertDialog != null) {
                alertDialog.show()
            }

        }


//        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
//            println("chceked radio $checkedId");

        return view
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//       recyclerViewQuestions  = view?.findViewById<RecyclerView>(R.id.recycler_view_questions)!!
//        recyclerViewQuestions.setHasFixedSize(true)
//        recyclerViewQuestions.layoutManager =
//            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
//
//    }


    private fun setQuestionOnScreen(view: View) {
        println("index $index")
        questionNumber.text = "${(index + 1).toString()}."

        if (index < 15) {
            question.setText(questionsFromDatabase?.get(index)?.question);

            val questionId = questionsFromDatabase!!.get(index)?.id;
            println("qustion id $questionId")

            radioButton1.setText(questionsFromDatabase?.get(index)?.A)
            radioButton2.setText(questionsFromDatabase?.get(index)?.B)
            radioButton3.setText(questionsFromDatabase?.get(index)?.C)
            radioButton4.setText(questionsFromDatabase?.get(index)?.D)

            radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                selectedAnswer = when (checkedId) {
                    R.id.radioButton1 -> radioButton1.text.toString()
                    R.id.radioButton2 -> radioButton2.text.toString()
                    R.id.radioButton3 -> radioButton3.text.toString()
                    R.id.radioButton4 -> radioButton4.text.toString()
                    else -> ""
                }
                println("selectedId $selectedAnswer")

            }
        } else {
            println("do nothing ******")
            nextBtn.isEnabled = false
            submitBtn.isEnabled = true
//        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment()
//
//        Navigation.findNavController(view).navigate(action)

        }
    }

    private fun InsertQuestionsIntoDatabase(): List<Question> {
        val question1 = Question(
            "Who developed kotlin?",
            "IBM",
            "Google",
            "JetBrains",
            "Microsoft",
            "JetBrains"
        )

        val question2 = Question(
            "Which extension is responsible to save Kotlin files?",
            ".kot",
            ".android",
            ".src",
            ".kt or .kts",
            ".kt or .kts"
        )

        val question3 = Question(
            "How to do a multi-line comment in kotlin language?",
            "//",
            "--",
            "/**/",
            "None of the above",
            "/**/"
        )

        val question4 = Question(
            "Kotlin only works for supporting java language?",
            "true",
            "false",
            null,
            null,
            "false"
        )

        val question5 = Question(
            "The two types of constructors in Kotlin are?",
            "Primary and Secondary constructor",
            "First and the Second constructor",
            "Constant and Parametrized constructor",
            "None of the above",
            "Primary and Secondary constructor"
        )

        val question6 = Question(
            "Does Kotlin use 'static' key word?",
            "Yes",
            "No",
            null,
            null,
            "No"
        )

        val question7 = Question(
            "what handles null exceptions in kotlin?",
            "Sealed classes",
            "Lambda functions",
            "The Kotlin expression",
            "Elvis operator",
            "Elvis operator"
        )

        val question8 = Question(
            "The correct function to get the length of a string in Kotlin language is?",
            "str.length",
            "string(length)",
            "lengthof(str)",
            "None of these",
            "str.length"
        )

        val question9 = Question(
            "Kotlin language name came from the 'Kotlin Island' of Russia?",
            "true",
            "false",
            null,
            null,
            "true"
        )

        val question10 = Question(
            "The function to print a line in Kotlin is?",
            "Printline()",
            "println()",
            "print()",
            "B and C",
            "B and C"
        )

        val question11 = Question(
            "Under which license Kotlin was developed?",
            "1.1",
            "1.5",
            "2.0",
            "2.1",
            "2.0"
        )

        val question12 = Question(
            "In Kotlin, the default visibility modifier is?",
            "sealed",
            "public",
            "protected",
            "private",
            "public"
        )

        val question13 = Question(
            "Does Kotlin support OOPs and Procedural Programming?",
            "Yes",
            "No",
            null,
            null,
            "Yes"
        )

        val question14 = Question(
            "What defines a sealed class in Kotlin?",
            "Its another name for an abstract class",
            "It represents restricted class hierarchies",
            "It is used in every Kotlin program",
            "None of the above",
            "It represents restricted class hierarchies"
        )

        val question15 = Question(
            "The default behavior of classes in Kotlin is?",
            "All classes are protected",
            "All classes are sealed",
            "All classes are final",
            "All classes are public",
            "All classes are final"
        )

        return listOf(
            question1,
            question2,
            question3,
            question4,
            question5,
            question6,
            question7,
            question8,
            question9,
            question10,
            question11,
            question12,
            question13,
            question14,
            question15,
        )

    }

}