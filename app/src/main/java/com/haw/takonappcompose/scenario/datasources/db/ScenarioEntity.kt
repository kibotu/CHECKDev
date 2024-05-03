package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("scenarios")
data class ScenarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)