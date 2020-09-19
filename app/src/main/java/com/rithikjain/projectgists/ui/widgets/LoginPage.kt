package com.rithikjain.projectgists.ui.widgets

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.rithikjain.projectgists.ui.activities.MainActivityAmbient

private lateinit var auth: FirebaseAuth

@Composable
fun LoginPage(onLoggedIn: () -> Unit) {
  auth = FirebaseAuth.getInstance()
  val activity = MainActivityAmbient.current

  Scaffold(
    topBar = {
      TopAppBar(
        { Text(text = "Login") },
        backgroundColor = MaterialTheme.colors.background
      )
    },
    bodyContent = {
      Button(onClick = handleLogin(onLoggedIn, activity)) {
        Text(text = "Login")
      }
    }
  )
}

fun handleLogin(onLoggedIn: () -> Unit, activity: Activity): () -> Unit = {
  val provider = OAuthProvider.newBuilder("github.com")
  provider.scopes = arrayListOf("read:user", "gist")

  val pendingResult = auth.pendingAuthResult
  if (pendingResult != null) {
    pendingResult.addOnSuccessListener {
      onLoggedIn()
    }.addOnFailureListener {
      Log.e("LOGIN", "Failure logging in", it)
    }
  } else {
    auth.startActivityForSignInWithProvider(activity, provider.build())
      .addOnSuccessListener {
        val credential = it.credential as OAuthCredential

        onLoggedIn()
      }.addOnFailureListener {
        Log.e("LOGIN", "Failure logging in", it)
      }
  }
}