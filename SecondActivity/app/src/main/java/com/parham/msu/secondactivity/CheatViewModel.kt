package com.parham.msu.secondactivity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val IS_ANSWER_SHOWN_KEY = "IS_ANSWER_SHOWN_KEY"

class CheatViewModel(private val savedStateHandle: SavedStateHandle) :ViewModel() {

    var isAnswerShown: Boolean
        get() = savedStateHandle.get(IS_ANSWER_SHOWN_KEY) ?: false
        set(value) = savedStateHandle.set(IS_ANSWER_SHOWN_KEY, value)
}