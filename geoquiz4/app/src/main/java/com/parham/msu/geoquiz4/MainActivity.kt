package com.parham.msu.geoquiz4


// MainActivity.kt
import QuizListener
import QuizViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parham.msu.geoquiz4.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), QuizListener {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizViewModel.setQuizListener(this)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            setButtonState(false)
        }

        binding.FalseButton.setOnClickListener {
            checkAnswer(false)
            setButtonState(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            setButtonState(true)
        }

        binding.questionTextview.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            setButtonState(true)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            setButtonState(true)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextview.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        if (userAnswer == correctAnswer) {
            quizViewModel.correctAnswerCount++
        }

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun setButtonState(enabled: Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.FalseButton.isEnabled = enabled
    }

    override fun onQuizCompleted(score: Int) {
        val message = "Quiz complete! Your score is $score%"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}



   /* override fun onStart() {
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
*/

