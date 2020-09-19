package com.rithikjain.projectgists.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.rithikjain.projectgists.ui.themes.EzGistsTheme
import com.rithikjain.projectgists.ui.widgets.HomePage
import com.rithikjain.projectgists.ui.widgets.LoginPage

val MainActivityAmbient = ambientOf<Activity> { error("Needs to be provided") }
val DataStoreAmbient = ambientOf<DataStore<Preferences>> { error("Needs to be provided") }

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Providers(
        MainActivityAmbient provides this,
        DataStoreAmbient provides ContextAmbient.current.createDataStore(name = "prefs")
      ) {
        EzGistsTheme {
          EzGistsApp()
        }
      }
    }
  }
}

@Composable
fun EzGistsApp() {
  val page =
    remember { mutableStateOf(if (FirebaseAuth.getInstance().currentUser != null) "home" else "login") }
  when (page.value) {
    "login" -> {
      LoginPage(onLoggedIn = {
        page.value = "home"
      })
    }

    "home" -> {
      HomePage()
    }
  }
}

@Preview
@Composable
fun PreviewApp() {
  EzGistsTheme {
    HomePage()
  }
}