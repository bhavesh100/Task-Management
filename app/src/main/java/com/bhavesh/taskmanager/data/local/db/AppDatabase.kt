package com.bhavesh.taskmanager.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bhavesh.taskmanager.data.local.dao.TaskDao
import com.bhavesh.taskmanager.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
