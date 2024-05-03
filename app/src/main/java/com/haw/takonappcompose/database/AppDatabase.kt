package com.haw.takonappcompose.database

import android.transition.Scene
import androidx.room.Database
import androidx.room.RoomDatabase
import com.haw.takonappcompose.BuildConfig
import com.haw.takonappcompose.scenario.datasources.db.ActionDao
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseDao
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioDao
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity

@Database(
    entities = [
        AnswerEntity::class,
        RoleEntity::class,
        ScenarioEntity::class,
        PhaseEntity::class,
        ActionEntity::class
    ], version = BuildConfig.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao
    abstract fun roleDao(): RoleDao
    abstract fun scenarioDao(): ScenarioDao
    abstract fun phaseDao(): PhaseDao
    abstract fun actionDao(): ActionDao
}