package com.parham.msu.secondactivity


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProvider
import com.parham.msu.secondactivity.databinding.ActivityCheatBinding
import com.parham.msu.secondactivity.databinding.ActivityMainBinding


 const val EXTRA_ANSWER_SHOWN = "com.parham.msu.secondActivity.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.parham.msu.secondActivity.answer_is_true"

//private lateinit var binding: ActivityCheatBinding
class CheatActivity : AppCompatActivity() {

    private lateinit var viewModel: CheatViewModel
    private var answerIsTrue = false
    private var isAnswerButtonClicked = false // New variable to track button state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        viewModel = ViewModelProvider(this).get(CheatViewModel::class.java)

        // Restore cheating status if available
        if (savedInstanceState != null) {
            viewModel.cheated = savedInstanceState.getBoolean("cheated")
            isAnswerButtonClicked = savedInstanceState.getBoolean("isAnswerButtonClicked")
            if (isAnswerButtonClicked) {
                showAnswer(binding)
            }
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer(binding)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("cheated", viewModel.cheated)
        outState.putBoolean("isAnswerButtonClicked", isAnswerButtonClicked)
    }

    private fun showAnswer(binding: ActivityCheatBinding) {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
        setAnswerShownResult(true)
        viewModel.cheated = true
        isAnswerButtonClicked = true
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
