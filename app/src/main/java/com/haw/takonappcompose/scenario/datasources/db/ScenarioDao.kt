package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import retrofit2.http.GET

@Dao
interface ScenarioDao {

    @Upsert
    suspend fun upsert(scenario: ScenarioEntity)

//    @Query("SELECT * FROM `roles`")
//    fun getScenarioById(): Flow<List<ScenarioEntity>>

    @Query("SELECT * FROM `scenarios` WHERE id = :id")
    suspend fun getScenarioById(id: Int): ScenarioEntity?
}