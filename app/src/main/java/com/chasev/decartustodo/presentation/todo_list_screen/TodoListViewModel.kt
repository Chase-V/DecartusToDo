package com.chasev.decartustodo.presentation.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.data.repository.WorkResult
import com.chasev.decartustodo.data.room.RoomRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.Date

class TodoListViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private val tasksList = repository.getListOfTasksFlow()

    var uiState = tasksList.map { tasks ->
        when (tasks) {
            is WorkResult.Error -> TasksListUiState(isError = true)
            is WorkResult.Loading -> TasksListUiState(isLoading = true)
            is WorkResult.Success -> {
                TasksListUiState(tasks = tasks.data.reversed().sortedBy { !it.isImportant })
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksListUiState(isLoading = true)
    )

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    fun updateTask(task: TodoTask) {
        viewModelScope.launch {
            repository.editTask(task)
        }
    }

    fun addSampleTasks() {
        viewModelScope.launch {
            val tasks = arrayOf(
                TodoTask(
                    taskText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                    isImportant = false,
                    isComplete = false,
                    whenDate = Date(Date().time + 21000),
                    createdAt = Date()
                ),
                TodoTask(
                    taskText = "Lorem ipsum dolor sit amet, consectetur",
                    isImportant = false,
                    isComplete = false,
                    whenDate = null,
                    createdAt = Date()
                ),
                TodoTask(
                    taskText = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                    isImportant = true,
                    isComplete = false,
                    whenDate = Date(Date().time + 3000000000),
                    createdAt = Date()
                ),
                TodoTask(
                    taskText = "Lorem ipsum dolor sit amet",
                    isImportant = false,
                    isComplete = false,
                    whenDate = Date(Date().time + 45000000000),
                    createdAt = Date()
                ),
                TodoTask(
                    taskText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                    isImportant = true,
                    isComplete = false,
                    whenDate = Timestamp(Date().time + 7000000000),
                    createdAt = Date()
                )
            )
            tasks.forEach { repository.addTask(it) }
        }
    }
}

data class TasksListUiState(
    val tasks: List<TodoTask> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
