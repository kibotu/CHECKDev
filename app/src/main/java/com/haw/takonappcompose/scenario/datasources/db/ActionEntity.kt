package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("actions")
class ActionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("roleId")
    val roleId: String,
    @ColumnInfo("input")
    val input: String,
    @ColumnInfo("output")
    val output: String,
    @ColumnInfo("phase_id")
    val phaseId: Int
)
