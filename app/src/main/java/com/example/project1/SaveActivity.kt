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
    private lateinit var existing_food_list: Button
    private lateinit var shopping_list: Button


//    private val bbViewModel: BBViewModel by lazy {
//        ViewModelProviders.of(this).get(BBViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

//        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
//        bbViewModel.currentIndex = currentIndex

        existing_food_list = findViewById(R.id.existing_food_list)
        shopping_list = findViewById(R.id.shopping_list)

        existing_food_list.setOnClickListener { view: View ->

            val intent = ListActivity.newIntent(this@SaveActivity, "Food In Fridge")
            startActivity(intent)
            Log.i(TAG, "onClickListener for food in fridge")
        }

        shopping_list.setOnClickListener { view: View ->

            val intent = ListActivity.newIntent(this@SaveActivity, "Shopping List")
            startActivity(intent)
            Log.i(TAG, "onClickListener for shopping list")
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