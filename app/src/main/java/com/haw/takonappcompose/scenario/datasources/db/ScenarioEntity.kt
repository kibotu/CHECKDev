package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("scenario")
data class ScenarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("task")
    val task: String
)