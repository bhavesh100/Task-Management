package com.bhavesh.taskmanager.domain.usecase

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val updateTask: UpdateTask,
    val deleteTask: DeleteTask
)