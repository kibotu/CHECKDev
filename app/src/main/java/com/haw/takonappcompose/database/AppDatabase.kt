package com.haw.takonappcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haw.takonappcompose.BuildConfig

@Database(entities = [AnswerEntity::class], version = BuildConfig.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao
}