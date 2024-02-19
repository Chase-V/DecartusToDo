package com.chasev.decartustodo.data.repository

import java.util.Date

data class TodoTask(
    val taskId: String? = null,
    val taskText: String,
    val isImportant: Boolean = false,
    val isComplete: Boolean = false,
    val isDeleted: Boolean = false,
    val whenDate: Date? = null,
    val createdAt: Date = Date()
)
