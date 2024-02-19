package com.chasev.decartustodo.presentation.ArchiveScreen

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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    viewModel: ArchiveScreenViewModel = koinViewModel<ArchiveScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showCompleteDialog by rememberSaveable {
        mutableStateOf(emptyList<TodoTask>())
    }
    var showDeleteDialog by rememberSaveable {
        mutableStateOf("")
    }
    var showUndeleteDialog by rememberSaveable {
        mutableStateOf(emptyList<TodoTask>())
    }
    var showDeleteAllCompletedAndDeletedDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showDeleteAllDialog by rememberSaveable {
        mutableStateOf(false)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "DecartusToDo")
                },
                actions = {
                    IconButton(onClick = { showDeleteAllCompletedAndDeletedDialog = true }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                    IconButton(onClick = { showDeleteAllDialog = true }) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                    }
                }
            )
        },
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
                        text = stringResource(R.string.empty_archive_text),
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
                            onImportantChanged = { },
                            onCardClick = { showUndeleteDialog = listOf(item) },
                            onCardLongClick = { showDeleteDialog = item.taskId!! },
                            whenTask = item.whenDate
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }

            if (showCompleteDialog.isNotEmpty()) {
                ShowDialog(
                    onConfirmClick = {
                        viewModel.updateTask(
                            showCompleteDialog.first()
                                .copy(isComplete = !showCompleteDialog.first().isComplete)
                        ); showCompleteDialog = emptyList()
                    },
                    onDismiss = { showCompleteDialog = emptyList() },
                    dialogText = "Вернуть задачу из архива?"
                )
            }

            if (showUndeleteDialog.isNotEmpty()) {
                com.chasev.decartustodo.presentation.todo_list_screen.ShowDialog(
                    onConfirmClick = {
                        viewModel.updateTask(
                            showUndeleteDialog.first()
                                .copy(isDeleted = false, isComplete = false)
                        )
                        showUndeleteDialog = emptyList()
                    },
                    onDismiss = { showUndeleteDialog = emptyList() },
                    dialogText = "Вернуть задачу в актуальные?"
                )
            }

            if (showDeleteDialog.isNotEmpty()) {
                com.chasev.decartustodo.presentation.todo_list_screen.ShowDialog(
                    onConfirmClick = {
                        viewModel.deleteTask(showDeleteDialog)
                        showDeleteDialog = ""
                    },
                    onDismiss = { showDeleteDialog = "" },
                    dialogText = "Навсегда удалить задачу?"
                )
            }

            if (showDeleteAllCompletedAndDeletedDialog) {
                com.chasev.decartustodo.presentation.todo_list_screen.ShowDialog(
                    onConfirmClick = {
                        viewModel.deleteCompletedAndDeleted(); showDeleteAllCompletedAndDeletedDialog =
                        false
                    },
                    onDismiss = { showDeleteAllCompletedAndDeletedDialog = false },
                    dialogText = "Удалить ВСЕ выполненные и удаленные задачи?"
                )
            }
            if (showDeleteAllDialog) {
                com.chasev.decartustodo.presentation.todo_list_screen.ShowDialog(
                    onConfirmClick = {
                        viewModel.deleteAll(); showDeleteAllDialog = false
                    },
                    onDismiss = { showDeleteAllDialog = false },
                    dialogText = "Удалить абсолютно ВСЕ задачи?"
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