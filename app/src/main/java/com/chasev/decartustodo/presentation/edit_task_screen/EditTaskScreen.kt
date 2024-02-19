package com.chasev.decartustodo.presentation.edit_task_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chasev.decartustodo.presentation.todo_list_screen.components.StarCheckbox
import com.chasev.decartustodo.utils.dateToLocal
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    onTaskUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = koinViewModel<EditTaskViewModel>()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDatePicker: Boolean by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text(text = "Введите текст заметки:") },
            value = uiState.taskText,
            onValueChange = { viewModel.setTaskText(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StarCheckbox(
                isChecked = uiState.isImportant,
                onCheckedChanged = {
                    viewModel.setIsImportant(
                        it
                    )
                },
                size = 50.dp
            )

            Text(text = "Отметить как важное?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = { showDatePicker = true }) {
                Text(text = "Выбрать дату:")
            }

            Text(
                text = if (uiState.whenDate != null) {
                    dateToLocal(uiState.whenDate!!)
                } else {
                    ""
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.saveTask() }) {
            Text(text = "Сохранить")
        }

        LaunchedEffect(uiState.isSaved) {
            if (uiState.isSaved) {
                onTaskUpdate()
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(text = "Готово")
                        if (datePickerState.selectedDateMillis != null) {
                            viewModel.setDate(Date(datePickerState.selectedDateMillis!!))
                        }

                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }
    val context = LocalContext.current
    val errorText = uiState.taskSavingError?.let { stringResource(id = it) }
    LaunchedEffect(errorText) {
        if (errorText != null) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
        }
    }
}