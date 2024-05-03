package com.haw.takonappcompose.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromptAnswer(
    @SerialName("context")
    var context: List<Int>?,
    @SerialName("created_at")
    var createdAt: String?,
    @SerialName("done")
    var done: Boolean?,
    @SerialName("evar_count")
    var evarCount: Int?,
    @SerialName("evar_duration")
    var evarDuration: Long?,
    @SerialName("load_duration")
    var loadDuration: Long?,
    @SerialName("model")
    var model: String?,
    @SerialName("prompt_evar_count")
    var promptEvarCount: Int?,
    @SerialName("prompt_evar_duration")
    var promptEvarDuration: Long?,
    @SerialName("message")
    var message: String?,
    @SerialName("total_duration")
    var totalDuration: Long?
)

@Serializable
data class ChatAnswer(
    @SerialName("created_at")
    var createdAt: String?,
    @SerialName("done")
    var done: Boolean?,
    @SerialName("eval_count")
    var evalCount: Int?,
    @SerialName("eval_duration")
    var evalDuration: Long?,
    @SerialName("load_duration")
    var loadDuration: Long?,
    @SerialName("message")
    var message: ChatMessage?,
    @SerialName("model")
    var model: String?,
    @SerialName("prompt_eval_duration")
    var promptEvalDuration: Long?,
    @SerialName("total_duration")
    var totalDuration: Long?
)

@Serializable
data class ChatMessage(
    @SerialName("content")
    var content: String?,
    @SerialName("role")
    var role: String?
)