package com.rithikjain.projectgists.api

import com.rithikjain.projectgists.models.GistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface WebService {

  @GET("/gists")
  suspend fun getGists(
    @Header("accept") accept: String = "application/vnd.github.v3+json",
    @Header("Authorization") token: String
  ): Response<List<GistResponse>>
}