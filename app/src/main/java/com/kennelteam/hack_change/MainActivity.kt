package com.kennelteam.hack_change

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AccessTokenManager.setup(this)
        Networker.setup(this)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id)).build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.button.setOnClickListener {
            Log.i("Test!!!", "STAAAART!!!")
            signIn()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_flow, R.id.navigation_edit_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    private fun signIn() {
        Log.i("Test!!!", "signIn")
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        Log.i("Test!!!", "created intent")
        startActivityForResult(signInIntent, 179)
        Log.i("Test!!!", "started activity")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Test!!!", "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 179) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.i("Test!!!", "creating task")
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.i("Test!!!", "taskCreated")
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            Log.i("Test!!!", "handleSignInResult")
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            Log.i("Test!!!", account.idToken!!)
            Log.i("Test!!!", account.displayName!!)
            // Signed in successfully, show authenticated UI.
            // updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.i("Test!!!", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }
}