package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("actions")
class ActionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("roleId")
    var roleId: String,
    @ColumnInfo("input")
    var input: String?,
    @ColumnInfo("output")
    var output: String?,
    @ColumnInfo("phase_id")
    var phaseId: Int
)
