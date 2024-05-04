package com.haw.takonappcompose.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    @SerialName("model")
    val model: String,
    @SerialName("messages")
    val messages: List<Message>,
    @SerialName("stream")
    val stream: Boolean,
    @SerialName("options")
    val options: Options?,
)

@Serializable
data class Options(
    @SerialName("temperature")
    val temperature: Float,
)

@Serializable
data class Message(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String,
)

val Message.fromUser: Boolean
    get() = role == "user"
