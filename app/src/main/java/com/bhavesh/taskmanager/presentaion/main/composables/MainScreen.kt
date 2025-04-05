package com.bhavesh.taskmanager.presentaion.main.composables

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhavesh.taskmanager.domain.model.Task
import com.bhavesh.taskmanager.presentaion.viewmodel.TaskViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val tasks = viewModel.taskList
    val error = viewModel.error
    val editingTask = viewModel.editingTask
    var showDialog by remember { mutableStateOf(false) }
    val successMessage = viewModel.successMessage
    val firebaseAnalytics = remember { Firebase.analytics }
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(successMessage) {
        successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Task Manager") })
        },
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.startEditing(null) // New task
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (error != null) {
                Text(text = error, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onCheckedChange = {
                            viewModel.updateTask(task.copy(isCompleted = it))
                            firebaseAnalytics.logEvent("task_completed", Bundle().apply {
                                putString("task_title", task.title)
                            })
                        },
                        onEditClick = {
                            viewModel.startEditing(task) // Edit mode
                            showDialog = true
                            firebaseAnalytics.logEvent("task_edited", Bundle().apply {
                                putString("task_title", task.title)
                            })
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddTaskDialog(
            titleInit = editingTask?.title.orEmpty(),
            descriptionInit = editingTask?.description.orEmpty(),
            onDismiss = {
                showDialog = false
                viewModel.startEditing(null)
            },
            onSave = { title, description ->
                if (editingTask == null) {
                    viewModel.addTask(Task(title = title, description = description, isCompleted = false))
                    firebaseAnalytics.logEvent("task_added", Bundle().apply {
                        putString("task_title", title)
                    })
                } else {
                    viewModel.updateTask(editingTask.copy(title = title, description = description))
                    firebaseAnalytics.logEvent("task_edited", Bundle().apply {
                        putString("task_title", title)
                    })
                }
                showDialog = false
                viewModel.startEditing(null)
            },
            onDelete = editingTask?.let {
                {
                    viewModel.deleteTask(it)
                    showDialog = false
                    firebaseAnalytics.logEvent("task_deleted", Bundle().apply {
                        putString("task_title", it.title)
                    })
                    viewModel.startEditing(null)
                }
            }
        )
    }
}
