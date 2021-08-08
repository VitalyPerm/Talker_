package com.elvitalya.talker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var userName: String? = null
    private var userPhotoUrl: String? = null

    private var fireBaseAuth: FirebaseAuth? = null
    private var fireBaseUser: FirebaseUser? = null

    private var googleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()

        userName = ANON
        fireBaseAuth = FirebaseAuth.getInstance()
        fireBaseUser = fireBaseAuth?.currentUser


        if (fireBaseUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        } else {
            userName = fireBaseUser?.displayName

            if (fireBaseUser?.photoUrl != null){
                userPhotoUrl = fireBaseUser?.photoUrl.toString()
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(TAG, "on connection failed $p0")
        Toast.makeText(this, "Google play services error", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val TAG = "MainActivity"
        const val ANON = "anon"
    }
}