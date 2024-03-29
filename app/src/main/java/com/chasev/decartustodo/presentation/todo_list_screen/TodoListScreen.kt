package com.chasev.decartustodo.presentation.todo_list_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chasev.decartustodo.R
import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.presentation.todo_list_screen.components.TodoCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    addTask: () -> Unit,
    editTask: (String) -> Unit,
    onNavigateToArchivePressed: () -> Unit,
    viewModel: TodoListViewModel = koinViewModel<TodoListViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(emptyList<TodoTask>())
    }

    var showAddSampleDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showCompleteDialog by rememberSaveable {
        mutableStateOf(emptyList<TodoTask>())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "DecartusToDo")
                },
                actions = {
                    IconButton(onClick = onNavigateToArchivePressed) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                    IconButton(onClick = { showAddSampleDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addTask) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { scaffoldPaddingValues ->
        Column(modifier = Modifier.padding(scaffoldPaddingValues)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (uiState.tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.empty_list_text),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn {
                if (uiState.tasks.isNotEmpty()) {
                    items(uiState.tasks) { item ->
                        Log.d("MyLog", "TodoListScreen: ${uiState.tasks.filter { it.isComplete }}")
                        TodoCard(
                            isCompleteChecked = item.isComplete,
                            isImportantChecked = item.isImportant,
                            taskText = item.taskText,
                            onCheckedChanged = {
                                showCompleteDialog = listOf(item)
                            },
                            onImportantChanged = { viewModel.updateTask(item.copy(isImportant = !item.isImportant)) },
                            onCardClick = { editTask(item.taskId!!) },
                            onCardLongClick = { showDeleteDialog = listOf(item) },
                            whenTask = item.whenDate
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }

            if (showAddSampleDialog) {
                ShowDialog(
                    onConfirmClick = { viewModel.addSampleTasks(); showAddSampleDialog = false },
                    onDismiss = { showAddSampleDialog = false },
                    dialogText = "Добавить 5 задач для примера?"
                )
            }

            if (showDeleteDialog.isNotEmpty()) {
                ShowDialog(
                    onConfirmClick = {
                        viewModel.updateTask(
                            showDeleteDialog.first()
                                .copy(isDeleted = !showDeleteDialog.first().isDeleted)
                        )
                        showDeleteDialog = emptyList()
                    },
                    onDismiss = { showDeleteDialog = emptyList() },
                    dialogText = "Поместить задачу в архив?"
                )
            }

            if (showCompleteDialog.isNotEmpty()) {
                ShowDialog(
                    onConfirmClick = {
                        viewModel.updateTask(
                            showCompleteDialog.first()
                                .copy(isComplete = !showCompleteDialog.first().isComplete)
                        )
                        showCompleteDialog = emptyList()
                    },
                    onDismiss = { showCompleteDialog = emptyList() },
                    dialogText = "Пометить задачу выполненной и поместить в архив?"
                )
            }

        }
    }
}

@Composable
fun ShowDialog(
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
    dialogText: String
) {
    AlertDialog(
        title = { Text(text = dialogText) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Нет")
            }
        }
    )
}