package com.rithikjain.projectgists.ui.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomePage() {
  Scaffold(
    topBar = {
      TopAppBar(
        {
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
          ) {
            Text(text = "My Gists")
            Text(text = FirebaseAuth.getInstance().currentUser?.displayName.toString())
          }
        },
        backgroundColor = MaterialTheme.colors.background
      )
    },
    bodyContent = {
      Column {
        GistItem(title = "Hi there", body = "Sample text")
        GistItem(title = "Hi there", body = "Sample text")
      }
    }
  )
}