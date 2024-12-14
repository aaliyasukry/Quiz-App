package lk.nibm.ku.hdse233f.quizapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lk.nibm.ku.hdse233f.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questions = arrayOf(
        "What is the capital of France?",
        "Which is the largest planet in our solar system?",
        "What is the chemical symbol for water?"
    )
    private val option = arrayOf(
        arrayOf("Berlin", "Madrid", "Paris", "Rome"),
        arrayOf("Earth", "Mars", "Jupiter", "Saturn"),
        arrayOf("H2O", "O2", "CO2", "NaCl")
    )
    private val answer = arrayOf(2, 2, 0) // Correct answers (indexes)
    private var currentQIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Display first question
        displayQuestion()

        // Set option buttons
        binding.btnOption1.setOnClickListener { checkAnswer(0) }
        binding.btnOption2.setOnClickListener { checkAnswer(1) }
        binding.btnOption3.setOnClickListener { checkAnswer(2) }
        binding.btnOption4.setOnClickListener { checkAnswer(3) }

        // Restart button
        binding.btnRestart.setOnClickListener {
            restartQuiz()
        }
    }

    private fun correctButtonColor(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.btnOption1.setBackgroundColor(Color.GREEN)
            1 -> binding.btnOption2.setBackgroundColor(Color.GREEN)
            2 -> binding.btnOption3.setBackgroundColor(Color.GREEN)
            3 -> binding.btnOption4.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColor(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.btnOption1.setBackgroundColor(Color.RED)
            1 -> binding.btnOption2.setBackgroundColor(Color.RED)
            2 -> binding.btnOption3.setBackgroundColor(Color.RED)
            3 -> binding.btnOption4.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        val defaultColor = Color.rgb(98, 78, 136) // Default button color
        binding.btnOption1.setBackgroundColor(defaultColor)
        binding.btnOption2.setBackgroundColor(defaultColor)
        binding.btnOption3.setBackgroundColor(defaultColor)
        binding.btnOption4.setBackgroundColor(defaultColor)
    }

    private fun showResult() {
        binding.questionTxt.text = "Quiz Completed! Your Score: $score"
        binding.btnRestart.isEnabled = true
    }

    private fun displayQuestion() {
        binding.questionTxt.text = questions[currentQIndex]
        binding.btnOption1.text = option[currentQIndex][0]
        binding.btnOption2.text = option[currentQIndex][1]
        binding.btnOption3.text = option[currentQIndex][2]
        binding.btnOption4.text = option[currentQIndex][3]
        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = answer[currentQIndex]

        // Show colors for correct/incorrect answers
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColor(selectedAnswerIndex)
        } else {
            wrongButtonColor(selectedAnswerIndex)
            correctButtonColor(correctAnswerIndex)
        }

        binding.scoretxt.text = "Score: $score"

        // Delay before showing next question or results
        binding.questionTxt.postDelayed({
            if (currentQIndex < questions.size - 1) {
                currentQIndex++
                displayQuestion()
            } else {
                showResult()
            }
        }, 1000) // 1 second delay
    }

    private fun restartQuiz() {
        currentQIndex = 0
        score = 0
        displayQuestion()
        binding.btnRestart.isEnabled = false
    }
}
