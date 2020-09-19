package com.rithikjain.projectgists.ui.widgets

import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.rithikjain.projectgists.models.Error
import com.rithikjain.projectgists.models.GistResponse
import com.rithikjain.projectgists.models.Loading
import com.rithikjain.projectgists.models.Success
import com.rithikjain.projectgists.ui.activities.DataStoreAmbient
import com.rithikjain.projectgists.ui.activities.ViewModelAmbient
import com.rithikjain.projectgists.ui.themes.vividPink
import com.rithikjain.projectgists.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map


@Composable
fun HomePage() {
  val vm = ViewModelAmbient.current
  val datastore = DataStoreAmbient.current
  launchInComposition {
    datastore.data.map { it[Constants.ACCESS_TOKEN].toString() }.collect { token ->
      vm.getGists("token $token")
    }

  }

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
      when (vm.gists) {
        is Success -> {
          LazyColumnFor((vm.gists as Success<List<GistResponse>>).data) { item ->
            GistItem(
              title = item.files.entries.first().value.filename,
              body = item.description
            )
          }
        }

        is Loading -> {
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(
              modifier = Modifier.fillMaxHeight(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
                CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally), color = vividPink)
              }
            }
          }
        }

        is Error -> {

        }
      }
    }
  )
}