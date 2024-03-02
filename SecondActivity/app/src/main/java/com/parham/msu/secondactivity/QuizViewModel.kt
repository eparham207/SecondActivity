package com.parham.msu.secondactivity

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CORRECT_ANSWER_KEY = "CORRECT_ANSWER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    /*init{
        Log.d(TAG, "QuizViewModel instance created")
    }

    override fun onCleared(){
        super.onCleared()

        Log.d(TAG,"QuizViewModel instance about to be destroyed")
    }*/
    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true),
        Question(R.string.question_ocean, answer = true),
        Question(R.string.question_mideast, answer = false),
        Question(R.string.question_Africa, answer = false),
        Question(R.string.question_americas, answer = true),
        Question(R.string.question_asia, answer = true)
    )
    private var currentIndex:Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY)?:0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var correctAnswerCount: Int
        get() = savedStateHandle.get(CORRECT_ANSWER_KEY)?:0
        set(value) = savedStateHandle.set(CORRECT_ANSWER_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        currentIndex = if (currentIndex > 0) {
            (currentIndex - 1) % questionBank.size
        } else {
            questionBank.size - 1 // Set currentIndex to the last index if it's already 0
        }
    }
    fun zero() {
        correctAnswerCount = 0
    }

    fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == currentQuestionAnswer) {
            incrementCorrectAnswerCount()
        }
    }

    private fun incrementCorrectAnswerCount() {
        correctAnswerCount++
    }

    fun isLastQuestion(): Boolean {
        return currentIndex == questionBank.size - 1
    }
    fun calculatePercentage(): Double {
        return (correctAnswerCount * 100.0 / questionBank.size)
    }
}