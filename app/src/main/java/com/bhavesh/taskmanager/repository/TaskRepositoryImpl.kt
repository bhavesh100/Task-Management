package com.bhavesh.taskmanager.repository

import com.bhavesh.taskmanager.data.local.dao.TaskDao
import com.bhavesh.taskmanager.data.remote.api.TaskApi
import com.bhavesh.taskmanager.domain.model.Task
import com.bhavesh.taskmanager.domain.model.toDomain
import com.bhavesh.taskmanager.domain.model.toDto
import com.bhavesh.taskmanager.domain.model.toEntity
import com.bhavesh.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun fetchTasks(): List<Task> {
        return try {
            val remoteTasks = api.getTasks()
            val localTasks = remoteTasks.map { it.toEntity() }
            localTasks.forEach { dao.insertTask(it) }
            dao.getTasks().map { it.toDomain() }
        } catch (e: Exception) {
            dao.getTasks().map { it.toDomain() }
        }
    }

    override suspend fun addTask(task: Task): Task {
        return try {
            val dto = task.toEntity().toDto()
            val added = api.addTask(dto)
            dao.insertTask(added.toEntity())
            added.toEntity().toDomain()
        } catch (e: Exception) {
            val localId = dao.insertTask(task.toEntity()).toInt()
            dao.getTasks().find { it.id == localId }?.toDomain() ?: task
        }
    }

    override suspend fun updateTask(task: Task): Task {
        return try {
            val updated = api.updateTask(task.id!!, task.toEntity().toDto())
            dao.updateTask(updated.toEntity())
            updated.toEntity().toDomain()
        } catch (e: Exception) {
            dao.updateTask(task.toEntity())
            task
        }
    }

    override suspend fun deleteTask(task: Task) {
        try {
            task.id?.let { api.deleteTask(it) }
        } catch (_: Exception) {}
        dao.deleteTask(task.toEntity())
    }
}
