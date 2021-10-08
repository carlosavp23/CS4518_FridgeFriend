package com.example.project1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "AddItemActivity"
//private const val KEY_INDEX = "index"
private const val EXTRA_SHOW_ADD =
    "com.example.project1.show_add"

class AddItemActivity : AppCompatActivity() {
    private lateinit var save_string: TextView
    private lateinit var food_name_input: EditText
    private lateinit var exp_date_input: EditText
    private lateinit var item_camera: ImageButton
    private lateinit var item_photo: ImageView
    private lateinit var submit_button: Button


//    private val bbViewModel: BBViewModel by lazy {
//        ViewModelProviders.of(this).get(BBViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

//        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
//        bbViewModel.currentIndex = currentIndex

        save_string = findViewById(R.id.save_string)
        item_camera = findViewById<ImageButton>(R.id.item_camera)
        item_photo = findViewById<ImageView>(R.id.item_photo)
        submit_button = findViewById(R.id.submit_button)
        food_name_input = findViewById(R.id.food_name_input)
        exp_date_input = findViewById(R.id.exp_date_input)

        submit_button.setOnClickListener { view: View ->
            val food_name_input = food_name_input.text.toString()
            val exp_date_input = exp_date_input.text.toString()
//            val intent = Intent(this, MainActivity::class.java)
            val intent = MainActivity.newIntent(this@AddItemActivity, food_name_input, exp_date_input)
            startActivity(intent)
            Log.i(TAG, "onClickListener for submit_button")
        }
        val show_save = intent.getBooleanExtra(EXTRA_SHOW_ADD, false)
        if(show_save){
            save_string.visibility = View.VISIBLE
        } else{
            save_string.visibility = View.INVISIBLE
        }
    }

    companion object {
        fun newIntent(packageContext: Context, show_save: Boolean): Intent {
            return Intent(packageContext, AddItemActivity::class.java).apply {
                putExtra(EXTRA_SHOW_ADD, show_save)
            }
        }
    }



}