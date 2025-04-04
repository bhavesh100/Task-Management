package com.bhavesh.taskmanager.presentaion.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavesh.taskmanager.domain.model.Task
import com.bhavesh.taskmanager.domain.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {
    var editingTask by mutableStateOf<Task?>(null)
        private set
    var taskList by mutableStateOf(listOf<Task>())
        private set
    var error by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                taskList = taskUseCases.getTasks()
            } catch (e: Exception) {
                error = "Failed to fetch tasks"
            }
        }
    }
    fun startEditing(task: Task?) {
        editingTask = task
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                val added = taskUseCases.addTask(task)
                taskList = taskList + added
                successMessage = "Task added successfully"
            } catch (e: Exception) {
                error = "Failed to add task"
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                val updated = taskUseCases.updateTask(task)
                taskList = taskList.map { t -> if (t.id == updated.id) updated else t }
                successMessage = "Task updated successfully"
            } catch (e: Exception) {
                error = "Failed to update task"
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskUseCases.deleteTask(task)
                taskList = taskList.filter { it.id != task.id }
                successMessage = "Task deleted successfully"
            } catch (e: Exception) {
                error = "Failed to delete task"
            }
        }
    }

    fun clearError() {
        error = null
    }

    fun clearSuccess() {
        successMessage = null
    }

}
