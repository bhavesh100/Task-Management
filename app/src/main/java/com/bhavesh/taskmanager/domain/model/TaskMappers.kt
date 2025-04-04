package com.bhavesh.taskmanager.domain.model

import com.bhavesh.taskmanager.data.local.entity.TaskEntity
import com.bhavesh.taskmanager.data.remote.dto.TaskDto

fun TaskDto.toEntity(): TaskEntity = TaskEntity(
    id = id ?: 0,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TaskEntity.toDto(): TaskDto = TaskDto(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id ?: 0,
    title = title,
    description = description,
    isCompleted = isCompleted
)