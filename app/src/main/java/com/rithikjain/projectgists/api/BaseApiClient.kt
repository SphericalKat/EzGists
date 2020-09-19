package com.rithikjain.projectgists.api

import com.rithikjain.projectgists.BuildConfig
import com.rithikjain.projectgists.models.ApiResult
import com.rithikjain.projectgists.models.Error
import com.rithikjain.projectgists.models.Success
import retrofit2.Response

open class BaseApiClient {
  protected suspend fun <T> getResult(request: suspend () -> Response<T>): ApiResult<T> {
    try {
      val response = request()
      return if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
          Success(body)
        } else {
          Error("Server response error")
        }
      } else {
        Error("${response.code()} ${response.message()}")
      }
    } catch (e: Exception) {
      val errorMessage = e.message ?: e.toString()
      return if (BuildConfig.DEBUG) {
        Error("Network call failed with message: $errorMessage")
      } else {
        Error("Check your internet connection!")
      }
    }
  }
}