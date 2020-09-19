package com.rithikjain.projectgists.models

data class GistResponse(
  val url: String,
  val forksURL: String,
  val commitsURL: String,
  val id: String,
  val nodeID: String,
  val gitPullURL: String,
  val gitPushURL: String,
  val htmlURL: String,
  val files: Map<String, File>,
  val public: Boolean,
  val createdAt: String,
  val updatedAt: String,
  val description: String,
  val comments: Long,
  val user: Any? = null,
  val commentsURL: String,
  val truncated: Boolean
)

data class File(
  val filename: String,
  val type: String,
  val language: String,
  val raw_url: String,
  val size: Int
)