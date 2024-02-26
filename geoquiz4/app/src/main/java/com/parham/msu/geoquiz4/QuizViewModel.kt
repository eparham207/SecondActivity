// QuizViewModel.kt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.parham.msu.geoquiz4.Question
import com.parham.msu.geoquiz4.R

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

interface QuizListener {
    fun onQuizCompleted(score: Int)
}

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var quizListener: QuizListener? = null

    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true),
        Question(R.string.question_ocean, answer = true),
        Question(R.string.question_mideast, answer = false),
        Question(R.string.question_Africa, answer = false),
        Question(R.string.question_americas, answer = true),
        Question(R.string.question_asia, answer = true)
    )
    var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var correctAnswerCount = 0

    init {
        checkIfLastQuestion()
    }

    private fun checkIfLastQuestion() {
        if (currentIndex == questionBank.size - 1) {
            quizListener?.onQuizCompleted(finalScore())
            reset()
        }
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        checkIfLastQuestion()
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
        checkIfLastQuestion()
    }

    fun setQuizListener(listener: QuizListener) {
        quizListener = listener
    }

    private fun finalScore(): Int {
        return ((correctAnswerCount * 100.0 / questionBank.size).toInt())
    }

    private fun reset() {
        correctAnswerCount = 0
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
}
