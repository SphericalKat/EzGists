package com.rithikjain.projectgists.models

sealed class ApiResult<out T>
data class Success<T>(val data: T) : ApiResult<T>()
data class Error<T>(val message: String) : ApiResult<T>()
class Loading<T>() : ApiResult<T>()