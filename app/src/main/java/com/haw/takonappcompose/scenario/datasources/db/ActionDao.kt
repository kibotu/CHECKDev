package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ActionDao {

    @Upsert
    suspend fun upsert(action: ActionEntity)

    @Delete
    suspend fun delete(action: ActionEntity)

    @Query("SELECT * FROM `actions`")
    suspend fun getAll(): List<ActionEntity>
}