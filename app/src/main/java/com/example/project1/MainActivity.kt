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

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val EXTRA_TEAM_A_NAME =
    "com.example.project1.team_a_name"
private const val EXTRA_TEAM_B_NAME =
    "com.example.project1.team_b_name"

class MainActivity : AppCompatActivity() {

    private val itemViewModel: ItemViewModel by lazy {
        ViewModelProviders.of(this).get(ItemViewModel::class.java)
    }

    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //old vvvv

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        itemViewModel.currentIndex = currentIndex

        intent.getStringExtra(EXTRA_TEAM_A_NAME)?.let { itemViewModel.setTeamAName(it) }
        intent.getStringExtra(EXTRA_TEAM_B_NAME)?.let { itemViewModel.setTeamBName(it) }

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

    public override fun onStart() {
        super.onStart()
        val mGmailSignIn = findViewById<SignInButton>(R.id.sign_in_button)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.w("Sign In: ", account.toString())
        mGmailSignIn.setOnClickListener {
            signIn()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.w("Sign In: ", completedTask.toString())
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account!!.idToken
            Log.w("Sign In: ", idToken.toString())
        } catch (e: ApiException) {
            Log.w("Sign In: ", "signInResult:failed code=" + e.statusCode)
        }
    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, bbViewModel.currentIndex)
    }



}