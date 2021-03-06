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
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val EXTRA_FOOD_NAME =
    "com.example.project1.food_name"
private const val EXTRA_EXP_DATE =
    "com.example.project1.exp_dat"


class MainActivity : AppCompatActivity() {

    private val foodViewModel: FoodViewModel by lazy {
        ViewModelProviders.of(this).get(FoodViewModel::class.java)
    }
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        intent.getStringExtra(EXTRA_FOOD_NAME)?.let { foodViewModel.setFood(it) }
    }

    public override fun onStart() {
        super.onStart()

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
//            val fragment = MainFragment()
            val fragment = GameListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, foodViewModel.currentIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miLogout) {
            Log.i(TAG, "Logout")
            auth.signOut()
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }
            return super.onOptionsItemSelected(item)

    }

    companion object {
        fun newIntent(packageContext: Context, food_name: String, exp_date: String): Intent {
            return Intent(packageContext, MainActivity::class.java).apply {
                putExtra(EXTRA_FOOD_NAME, food_name)
                putExtra(EXTRA_EXP_DATE, exp_date)

            }
        }
    }

}