package com.rithikjain.projectgists.api

import javax.inject.Inject

class WebClient @Inject constructor(private val webService: WebService) : BaseApiClient() {
  suspend fun getGists(token: String) = getResult {
    webService.getGists(token = token)
  }
}