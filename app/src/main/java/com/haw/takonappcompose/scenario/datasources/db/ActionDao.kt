package com.haw.takonappcompose.scenario.datasources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActionDao {

    @Insert
    suspend fun insert(action: ActionEntity)

    @Delete
    suspend fun delete(action: ActionEntity)

    @Query("SELECT * FROM `actions`")
    suspend fun getAll(): List<ActionEntity>
}