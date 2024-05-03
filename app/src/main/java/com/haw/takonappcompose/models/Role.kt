package com.haw.takonappcompose.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    @SerialName("model")
    val model: String,
    @SerialName("ip")
    val ip: String,
    @SerialName("icon")
    private val icon: String,
    @SerialName("bias")
    val bias: String,
    @SerialName("role")
    val role: String,
)