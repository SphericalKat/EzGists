package com.rithikjain.projectgists.utils

import androidx.datastore.preferences.preferencesKey

object Constants {
  val ACCESS_TOKEN = preferencesKey<String>("access_token")
}