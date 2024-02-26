package com.parham.msu.courtcountervm

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ScoreViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    init {
        // Initialize default values if they are not already present in savedStateHandle
        if (!savedStateHandle.contains("scoreTeamA")) {
            savedStateHandle.set("scoreTeamA", 0)
        }

        if (!savedStateHandle.contains("scoreTeamB")) {
            savedStateHandle.set("scoreTeamB", 0)
        }
    }

    var scoreTeamA: Int
        get() = savedStateHandle.get("scoreTeamA") ?: 0
        set(value) {
            savedStateHandle.set("scoreTeamA", value)
        }

    var scoreTeamB: Int
        get() = savedStateHandle.get("scoreTeamB") ?: 0
        set(value) {
            savedStateHandle.set("scoreTeamB", value)
        }

    fun addScoreTeamA(points: Int) {
        scoreTeamA += points
    }

    fun addScoreTeamB(points: Int) {
        scoreTeamB += points
    }

    fun resetScores() {
        scoreTeamA = 0
        scoreTeamB = 0
    }
    private var test = 1
}