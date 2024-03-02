package com.parham.msu.secondactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.parham.msu.secondactivity.databinding.ActivityMainBinding


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            setButtonState(false)
        }
        binding.FalseButton.setOnClickListener{
            checkAnswer(false)
            setButtonState(false)
        }

        binding.nextButton.setOnClickListener{
            //currentIndex = (currentIndex + 1) % questionBank.size
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

    private fun checkAnswer(userAnswer: Boolean) {
        quizViewModel.checkAnswer(userAnswer)

        val messageResId = if (userAnswer == quizViewModel.currentQuestionAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (quizViewModel.isLastQuestion()) {
            finalScore()
            reset()
        }
    }

    private fun setButtonState(enabled:Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.FalseButton.isEnabled = enabled

    }

    private fun finalScore() {
        val percentage = quizViewModel.calculatePercentage()
        val formattedPercentage = String.format("%.1f", percentage)

        val message = "Quiz complete! Your score is $formattedPercentage%"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun reset() {
        quizViewModel.zero()
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
