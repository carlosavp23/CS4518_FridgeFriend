package com.example.project1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

private const val TAG = "MainFragment"
private const val KEY_INDEX = "index"
private const val EXTRA_FOOD_NAME =
    "com.example.project1.food_name"


class MainFragment : Fragment() {
    private lateinit var food: Food
    private lateinit var team_a: TextView
    private lateinit var score_a: TextView
    private lateinit var add_3_a: Button
    private lateinit var add_2_a: Button
    private lateinit var free_throw_a: Button
    private lateinit var team_b: TextView
    private lateinit var score_b: TextView
    private lateinit var add_3_b: Button
    private lateinit var add_2_b: Button
    private lateinit var free_throw_b: Button
    private lateinit var reset: Button
    private lateinit var game_over: Button
    private lateinit var save_button: Button
    private lateinit var winner_a: TextView
    private lateinit var winner_b: TextView

    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        food = Food()
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        foodViewModel.currentIndex = currentIndex
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        team_a = view.findViewById(R.id.team_a)
        score_a = view.findViewById(R.id.score_a)
        add_3_a = view.findViewById(R.id.add_3_a)
        add_2_a = view.findViewById(R.id.add_2_a)
        free_throw_a = view.findViewById(R.id.free_throw_a)
        team_b = view.findViewById(R.id.team_b)
        score_b = view.findViewById(R.id.score_b)
        add_3_b = view.findViewById(R.id.add_3_b)
        add_2_b = view.findViewById(R.id.add_2_b)
        free_throw_b = view.findViewById(R.id.free_throw_b)
        reset = view.findViewById(R.id.reset)
        game_over = view.findViewById(R.id.game_over)
        save_button = view.findViewById(R.id.save_button)
        winner_a = view.findViewById(R.id.winner_a)
        winner_b = view.findViewById(R.id.winner_b)

        return view
    }

    override fun onStart() {
        super.onStart()


    }


/*    private fun makeClickable(): Void? {
        add_3_a.isClickable = true
        add_2_a.isClickable = true
        free_throw_a.isClickable = true
        add_3_b.isClickable = true
        add_2_b.isClickable = true
        free_throw_b.isClickable = true
        return null
    }

    private fun makeNotClickable(): Void? {
        add_3_a.isClickable = false
        add_2_a.isClickable = false
        free_throw_a.isClickable = false
        add_3_b.isClickable = false
        add_2_b.isClickable = false
        free_throw_b.isClickable = false
        return null
    }*/

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, foodViewModel.currentIndex)
    }

    companion object {
        fun newIntent(packageContext: Context, team_a_name: String): Intent {
            return Intent(packageContext, MainActivity::class.java).apply {
                putExtra(EXTRA_FOOD_NAME, team_a_name)
            }
        }
    }
}