package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PhaseDao {

    @Upsert
    suspend fun upsert(phaseEntity: PhaseEntity)

    @Delete
    suspend fun delete(phaseEntity: PhaseEntity)

    @Query("SELECT * FROM `phases`")
    suspend fun getAll(): List<PhaseEntity>?
}