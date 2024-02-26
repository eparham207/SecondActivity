package com.parham.msu.courtcountervm

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parham.msu.courtcountervm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ScoreViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restore scores if there's a saved instance state
        if (savedInstanceState != null) {
            viewModel.scoreTeamA = savedInstanceState.getInt("scoreTeamA", 0)
            viewModel.scoreTeamB = savedInstanceState.getInt("scoreTeamB", 0)
        }

        updateScores()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save scores in case of configuration change
        outState.putInt("scoreTeamA", viewModel.scoreTeamA)
        outState.putInt("scoreTeamB", viewModel.scoreTeamB)
        super.onSaveInstanceState(outState)
    }

    fun addOneForTeamA(v: View?) {
        viewModel.addScoreTeamA(1)
        updateScores()
    }

    fun addTwoForTeamA(v: View?) {
        viewModel.addScoreTeamA(2)
        updateScores()
    }

    fun addThreeForTeamA(v: View?) {
        viewModel.addScoreTeamA(3)
        updateScores()
    }

    fun addOneForTeamB(v: View?) {
        viewModel.addScoreTeamB(1)
        updateScores()
    }

    fun addTwoForTeamB(v: View?) {
        viewModel.addScoreTeamB(2)
        updateScores()
    }

    fun addThreeForTeamB(v: View?) {
        viewModel.addScoreTeamB(3)
        updateScores()
    }

    fun resetScore(v: View?) {
        viewModel.resetScores()
        updateScores()
    }

    private fun updateScores() {
        binding.teamAScore.text = viewModel.scoreTeamA.toString()
        binding.teamBScore.text = viewModel.scoreTeamB.toString()
    }
    private var test = 1
}