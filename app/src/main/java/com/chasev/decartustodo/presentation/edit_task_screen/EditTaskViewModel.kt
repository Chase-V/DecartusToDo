package com.chasev.decartustodo.presentation.edit_task_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chasev.decartustodo.R
import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.data.repository.WorkResult
import com.chasev.decartustodo.data.room.RoomRepository
import com.chasev.decartustodo.navigation.AppDestinationArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class EditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RoomRepository
) : ViewModel() {

    val taskId: String? = savedStateHandle[AppDestinationArgs.TASK_ID_ARG]

    private val _uiState = MutableStateFlow(AddEditTaskUiState())
    val uiState: StateFlow<AddEditTaskUiState> = _uiState.asStateFlow()

    init {
        if (taskId != null) {
            loadTask(taskId)
        }
    }

    private fun loadTask(taskId: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.getTaskFlow(taskId).first()

            if (result !is WorkResult.Success || result.data == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {
                val task = result.data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        taskText = task.taskText,
                        isImportant = task.isImportant,
                        whenDate = task.whenDate,
                    )
                }
            }
        }
    }

    fun saveTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isSaving = true) }

                repository.addTask(
                    TodoTask(
                        taskId = taskId,
                        taskText = _uiState.value.taskText,
                        isImportant = _uiState.value.isImportant,
                        whenDate = _uiState.value.whenDate
                    )
                )
                _uiState.update { it.copy(isSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(taskSavingError = R.string.saving_error) }
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

    fun setTaskText(newTaskText: String) {
        _uiState.update { it.copy(taskText = newTaskText) }
    }

    fun setIsImportant(newValue: Boolean) {
        _uiState.update { it.copy(isImportant = newValue) }
    }

    fun setDate(newDate: Date) {
        _uiState.update { it.copy(whenDate = newDate) }
    }

}

data class AddEditTaskUiState(
    val taskText: String = "",
    val isImportant: Boolean = false,
    val whenDate: Date? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val taskSavingError: Int? = null

)