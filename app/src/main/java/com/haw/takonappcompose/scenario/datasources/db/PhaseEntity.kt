package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.PrimaryKey

@Dao
data class PhaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "scenario_id")
    val scenarioId: Int
)