package com.haw.takonappcompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoleDao {

    @Insert
    suspend fun addRole(roleEntity: RoleEntity)

    @Query("SELECT * FROM `roles`")
    fun getRoles(): Flow<List<RoleEntity>>

    @Query("SELECT * FROM `roles`")
    suspend fun getAll(): List<RoleEntity>?
}