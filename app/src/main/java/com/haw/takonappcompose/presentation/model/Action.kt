package com.haw.takonappcompose.presentation.model

import com.haw.takonappcompose.database.RoleEntity

data class Action(
    val role: RoleEntity,
    val input: String = "",
    val output: String = "",
)
