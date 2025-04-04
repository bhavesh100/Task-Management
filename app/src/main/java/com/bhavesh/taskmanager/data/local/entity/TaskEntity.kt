package com.bhavesh.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavesh.taskmanager.data.remote.dto.TaskDto
import com.bhavesh.taskmanager.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)
