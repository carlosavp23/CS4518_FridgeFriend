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
private const val EXTRA_TEAM_A_NAME =
    "com.example.project1.team_a_name"
private const val EXTRA_TEAM_B_NAME =
    "com.example.project1.team_b_name"

class ListActivity : AppCompatActivity() {

    private val itemViewModel: ItemViewModel by lazy {
        ViewModelProviders.of(this).get(ItemViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_list)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        itemViewModel.currentIndex = currentIndex

//        intent.getStringExtra(EXTRA_TEAM_A_NAME)?.let { itemViewModel.setTeamAName(it) }
//        intent.getStringExtra(EXTRA_TEAM_B_NAME)?.let { itemViewModel.setTeamBName(it) }

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
        savedInstanceState.putInt(KEY_INDEX, itemViewModel.currentIndex)
    }

    companion object {
        fun newIntent(packageContext: Context, team_a_name: String, team_b_name: String): Intent {
            return Intent(packageContext, ListActivity::class.java).apply {
                putExtra(EXTRA_TEAM_A_NAME, team_a_name)
                putExtra(EXTRA_TEAM_B_NAME, team_b_name)
            }
        }
    }
}

