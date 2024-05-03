package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhaseDao {

    @Insert
    suspend fun insert(phaseEntity: PhaseEntity)

    @Delete
    suspend fun delete(phaseEntity: PhaseEntity)

    @Query("SELECT * FROM `phases`")
    suspend fun getAll(): List<PhaseEntity>
}