package com.example.mygame2
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mygame2.R

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var scoreTextView: TextView

    // Track high score
    var highScore: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreTextView = findViewById(R.id.score)

        // Set click listener for start button
        startBtn.setOnClickListener {
            startGame()
        }
    }

    // Method to start the game
    private fun startGame() {
        // Create new instance of GameView
        mGameView = GameView(this, this)

        // Set background resource for GameView
        mGameView.setBackgroundResource(R.drawable.dog111)

        // Add GameView to root layout
        rootLayout.addView(mGameView)

        // Hide start button and score text view
        startBtn.visibility = View.GONE
        scoreTextView.visibility = View.GONE
    }

    // Method to handle closing the game
    override fun closeGame(mScore: Int) {
        // Update high score if necessary
        if (mScore > highScore) {
            highScore = mScore
        }

        // Display score
        scoreTextView.text = "Score: $mScore\nHigh Score: $highScore"

        // Remove GameView from root layout
        rootLayout.removeView(mGameView)

        // Show start button and score text view
        startBtn.visibility = View.VISIBLE
        scoreTextView.visibility = View.VISIBLE
    }
}
