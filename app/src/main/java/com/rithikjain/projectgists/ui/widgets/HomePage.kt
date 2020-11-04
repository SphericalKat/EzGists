package com.rithikjain.projectgists.ui.widgets

import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.rithikjain.projectgists.R
import com.rithikjain.projectgists.models.Error
import com.rithikjain.projectgists.models.GistResponse
import com.rithikjain.projectgists.models.Loading
import com.rithikjain.projectgists.models.Success
import com.rithikjain.projectgists.ui.activities.DataStoreAmbient
import com.rithikjain.projectgists.ui.activities.ViewModelAmbient
import com.rithikjain.projectgists.ui.themes.EzGistsTheme
import com.rithikjain.projectgists.ui.themes.vividPink
import com.rithikjain.projectgists.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map


@Composable
fun HomePage() {
  val vm = ViewModelAmbient.current
  val datastore = DataStoreAmbient.current
  val user = FirebaseAuth.getInstance().currentUser

  LaunchedTask {
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
          ) {
            Text(text = "My Gists", color = vividPink)
            if (user != null) {
              NetworkImage(url = user.photoUrl.toString())
            }
          }
        },
        backgroundColor = MaterialTheme.colors.background,
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
          Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
            CircularProgressIndicator(
              modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
              color = vividPink
            )
          }
        }

        is Error -> {
          Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
            Text(
              modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
              text = "Something went wrong!"
            )
          }
        }
      }
    },
    floatingActionButton = {
      FloatingActionButton(onClick = {}, backgroundColor = MaterialTheme.colors.primary) {
        Icon(asset = Icons.Filled.Add)
      }
    }
  )
}

@Preview
@Composable
fun PreviewApp() {
  EzGistsTheme {
    HomePage()
  }
}