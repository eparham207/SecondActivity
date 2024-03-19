package com.parham.msu.secondactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.parham.msu.secondactivity.databinding.ActivityMainBinding


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatlauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

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
            quizViewModel.resetCheaterStatus()
            quizViewModel.moveToNext()
            quizViewModel.resetIsAnswered()
            updateQuestion()
            setButtonState(true)
        }
        binding.cheatButton.setOnClickListener{

            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent( this@MainActivity, answerIsTrue)
            cheatlauncher.launch(intent)
        }

        binding.questionTextview.setOnClickListener{
            quizViewModel.resetCheaterStatus()
            quizViewModel.moveToNext()
            quizViewModel.resetIsAnswered()
            updateQuestion()
            setButtonState(true)

        }
        binding.previousButton.setOnClickListener{
            quizViewModel.resetCheaterStatus()
            quizViewModel.moveToPrev()
            quizViewModel.resetIsAnswered()
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
        //quizViewModel.checkAnswer(userAnswer)
        quizViewModel.resetIsAnswered()

        /*val messageResId = if (userAnswer == quizViewModel.currentQuestionAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }*/

        val messageResId = when {
            quizViewModel.isCheater -> "Cheating is wrong."
            userAnswer == quizViewModel.currentQuestionAnswer -> "Correct!"
            else -> "Incorrect!"
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_INDEX_KEY, quizViewModel.currentIndex)
        outState.putBoolean(IS_ANSWERED_KEY, quizViewModel.isLastQuestion())
       // outState.putBoolean(USER_ANSWER_KEY, quizViewModel.userAnswer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedIndex = savedInstanceState.getInt(CURRENT_INDEX_KEY)
        val isAnswered = savedInstanceState.getBoolean(IS_ANSWERED_KEY, false)
        val userAnswer = savedInstanceState.getBoolean(USER_ANSWER_KEY, false)

        quizViewModel.currentIndex = savedIndex
       // quizViewModel.userAnswer = userAnswer

        if (isAnswered) {
            updateQuestion()
            setButtonState(true)
        }
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
