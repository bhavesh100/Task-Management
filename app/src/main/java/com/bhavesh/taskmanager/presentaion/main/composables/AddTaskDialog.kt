package com.bhavesh.taskmanager.presentaion.main.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskDialog(
    titleInit: String = "",
    descriptionInit: String = "",
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var title by remember { mutableStateOf(titleInit) }
    var description by remember { mutableStateOf(descriptionInit) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (titleInit.isEmpty()) "Add Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(title, description)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                if (onDelete != null) {
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    OutlinedButton(onClick = onDelete) {
                        Text("Delete", color = Color.Red)
                    }
                }
            }
        }
    )

}
