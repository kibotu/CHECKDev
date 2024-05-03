package com.haw.takonappcompose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("roles")
data class RoleEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("model")
    val model: String,
    @ColumnInfo("ip")
    val ip: String,
    @ColumnInfo("icon")
    val icon: String,
    @ColumnInfo("bias")
    val bias: String,
    @ColumnInfo("role")
    val role: String,
    @ColumnInfo("temperature")
    val temperature: String,
)