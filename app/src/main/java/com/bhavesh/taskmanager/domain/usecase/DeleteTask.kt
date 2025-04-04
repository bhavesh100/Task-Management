package com.bhavesh.taskmanager.domain.usecase

import com.bhavesh.taskmanager.domain.model.Task
import com.bhavesh.taskmanager.domain.repository.TaskRepository

class DeleteTask(private val repo: TaskRepository) {
    suspend operator fun invoke(task: Task) = repo.deleteTask(task)
}