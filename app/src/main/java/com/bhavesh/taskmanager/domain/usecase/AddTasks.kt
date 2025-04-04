package com.bhavesh.taskmanager.domain.usecase

import com.bhavesh.taskmanager.domain.model.Task
import com.bhavesh.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class AddTask @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task): Task = repository.addTask(task)
}