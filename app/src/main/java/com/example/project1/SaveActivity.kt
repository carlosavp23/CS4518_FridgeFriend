package com.example.project1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

private const val TAG = "SaveActivity"
//private const val KEY_INDEX = "index"
private const val EXTRA_SHOW_SAVE =
    "com.example.project1.show_save"

class SaveActivity : AppCompatActivity() {
    private lateinit var save_string: TextView
    private lateinit var new_game: Button
    private lateinit var game_history: Button
    private lateinit var team_a_input: EditText
    private lateinit var team_b_input: EditText

//    private val bbViewModel: BBViewModel by lazy {
//        ViewModelProviders.of(this).get(BBViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

//        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
//        bbViewModel.currentIndex = currentIndex

        save_string = findViewById(R.id.save_string)
        new_game = findViewById(R.id.new_game)
        game_history = findViewById(R.id.game_history)
        team_a_input = findViewById(R.id.team_a_input)
        team_b_input = findViewById(R.id.team_b_input)

        new_game.setOnClickListener { view: View ->
            val team_a_name = team_a_input.text.toString()
            val team_b_name = team_b_input.text.toString()
//            val intent = Intent(this, MainActivity::class.java)
            val intent = ListActivity.newIntent(this@SaveActivity, team_a_name)
            startActivity(intent)
            Log.i(TAG, "onClickListener for new_game")
        }
        val show_save = intent.getBooleanExtra(EXTRA_SHOW_SAVE, false)
        if(show_save){
            save_string.visibility = View.VISIBLE
        } else{
            save_string.visibility = View.INVISIBLE
        }
    }

    companion object {
        fun newIntent(packageContext: Context, show_save: Boolean): Intent {
            return Intent(packageContext, SaveActivity::class.java).apply {
                putExtra(EXTRA_SHOW_SAVE, show_save)
            }
        }
    }
}