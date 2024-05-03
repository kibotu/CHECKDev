package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioDao {

    @Insert
    suspend fun add(scenario: ScenarioEntity)

//    @Query("SELECT * FROM `roles`")
//    fun getScenarioById(): Flow<List<ScenarioEntity>>
}