package com.bhavesh.taskmanager.domain.repository

import com.bhavesh.taskmanager.domain.model.Task

interface TaskRepository {
    suspend fun fetchTasks(): List<Task>
    suspend fun addTask(task: Task): Task
    suspend fun updateTask(task: Task): Task
    suspend fun deleteTask(task: Task)
}