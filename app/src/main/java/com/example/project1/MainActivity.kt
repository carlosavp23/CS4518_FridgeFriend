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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import android.app.Activity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val EXTRA_TEAM_A_NAME =
    "com.example.project1.team_a_name"
private const val EXTRA_TEAM_B_NAME =
    "com.example.project1.team_b_name"

class MainActivity : AppCompatActivity() {

    private val bbViewModel: BBViewModel by lazy {
        ViewModelProviders.of(this).get(BBViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)

    }
    public override fun onStart() {
        super.onStart()

        intent.getStringExtra(EXTRA_TEAM_A_NAME)?.let { bbViewModel.setTeamAName(it) }
        intent.getStringExtra(EXTRA_TEAM_B_NAME)?.let { bbViewModel.setTeamBName(it) }

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = MainFragment()
//            val fragment = GameListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, bbViewModel.currentIndex)
    }

    companion object {
        fun newIntent(packageContext: Context, team_a_name: String, team_b_name: String): Intent {
            return Intent(packageContext, MainActivity::class.java).apply {
                putExtra(EXTRA_TEAM_A_NAME, team_a_name)
                putExtra(EXTRA_TEAM_B_NAME, team_b_name)
            }
        }
    }
}