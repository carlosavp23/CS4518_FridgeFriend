package com.example.project1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "ListActivity"
private const val KEY_INDEX = "index"
private const val EXTRA_LIST_NAME =
    "com.example.project1.food_name"


class ListActivity : AppCompatActivity() {

    private lateinit var list_name: TextView
    private lateinit var add_item_button: Button

    private val foodViewModel: FoodViewModel by lazy {
        ViewModelProviders.of(this).get(FoodViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_list)

        list_name = findViewById(R.id.list_name)
        add_item_button = findViewById(R.id.add_item_button)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        foodViewModel.currentIndex = currentIndex

//        intent.getStringExtra(EXTRA_TEAM_A_NAME)?.let { bbViewModel.setTeamAName(it) }
        intent.getStringExtra(EXTRA_LIST_NAME)?.let { foodViewModel.setCurrentList(it) }

        list_name.text = foodViewModel.currentList

        add_item_button.setOnClickListener { view: View ->

            val intent = AddFoodActivity.newIntent(this@ListActivity)
            startActivity(intent)
            Log.i(TAG, "onClickListener for add food item button")
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.list_fragment_container)
        if (currentFragment == null) {
//            val fragment = MainFragment()
            val fragment = GameListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.list_fragment_container, fragment)
                .commit()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, foodViewModel.currentIndex)
    }

    companion object {
        fun newIntent(packageContext: Context, list_name: String): Intent {
            return Intent(packageContext, ListActivity::class.java).apply {
                putExtra(EXTRA_LIST_NAME, list_name)
            }
        }
    }
}