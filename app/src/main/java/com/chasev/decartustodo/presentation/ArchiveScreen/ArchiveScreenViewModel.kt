package com.chasev.decartustodo.presentation.ArchiveScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.data.repository.WorkResult
import com.chasev.decartustodo.data.room.RoomRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArchiveScreenViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private val tasksList = repository.getCompletedAndDeleted()

    var uiState = tasksList.map { tasks ->
        when (tasks) {
            is WorkResult.Error -> TasksListUiState(isError = true)
            is WorkResult.Loading -> TasksListUiState(isLoading = true)
            is WorkResult.Success -> {
                TasksListUiState(tasks = tasks.data.reversed())
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksListUiState(isLoading = true)
    )


    fun updateTask(task: TodoTask) {
        viewModelScope.launch {
            repository.editTask(task)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun deleteCompletedAndDeleted() {
        viewModelScope.launch {
            repository.getCompletedAndDeleted()
        }
    }

}

data class TasksListUiState(
    val tasks: List<TodoTask> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
