package com.parham.msu.geoquiz4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import android.widget.Toast
import com.parham.msu.geoquiz4.databinding.ActivityMainBinding
import kotlin.math.roundToInt

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()


    var currentIndex = quizViewModel.currentIndex
    var questionBank = quizViewModel.questionBank


    private var correctAnswerCount = 0

//    private var totalQuestion = quizViewModel.getQuestionBank().size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            setButtonState(false)
        }
        binding.FalseButton.setOnClickListener{
            checkAnswer(false)
            setButtonState(false)
        }

        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            setButtonState(true)
        }

        binding.questionTextview.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            setButtonState(true)

        }
        binding.previousButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
            setButtonState(true)
        }

        updateQuestion()
    }
    private fun updateQuestion() {
        val questiontextResId = quizViewModel.currentQuestionText
        binding.questionTextview.setText(questiontextResId)
    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        if (userAnswer == correctAnswer) {
            correctAnswerCount++
        }

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if (quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            finalScore()
            reset()
        }
    }

    private fun setButtonState(enabled:Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.FalseButton.isEnabled = enabled

    }

    private fun finalScore() {
        val percentage = ((correctAnswerCount * 100.0 / quizViewModel.questionBank.size).roundToInt())
        val formattedPercentage = String.format("%.1f", percentage.toDouble())

        val message = "Quiz complete! Your score is $formattedPercentage%"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    private fun reset() {
        correctAnswerCount = 0
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}
