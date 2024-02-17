package com.chasev.decartustodo.data.repository

import java.sql.Timestamp

data class TodoTask(
    val taskId: String? = null,
    val taskText: String,
    val isImportant: Boolean,
    val isComplete: Boolean,
    val whenDate: Timestamp,
    val createdAt: Timestamp
)
