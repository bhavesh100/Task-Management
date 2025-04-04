package com.bhavesh.taskmanager.data.remote.dto

data class TaskDto(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)
