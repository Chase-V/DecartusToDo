package com.chasev.decartustodo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chasev.decartustodo.data.repository.TodoTask
import java.util.Date
import java.util.UUID

@Entity(tableName = "todoTasks")
data class TodoTaskEntity(
    @PrimaryKey var taskId: String,
    var taskText: String,
    var isImportant: Boolean,
    var isComplete: Boolean,
    var isDeleted: Boolean,
    var whenDate: Date?,
    var createdAt: Date
) {
    fun toTodoTask(): TodoTask = TodoTask(
        taskId = taskId,
        taskText = taskText,
        isImportant = isImportant,
        isComplete = isComplete,
        whenDate = whenDate,
        createdAt = createdAt
    )
}

fun TodoTask.toTodoTaskEntity(): TodoTaskEntity = TodoTaskEntity(
    taskId = taskId ?: UUID.randomUUID().toString(),
    taskText = taskText,
    isImportant = isImportant,
    isComplete = isComplete,
    isDeleted = isDeleted,
    whenDate = whenDate,
    createdAt = createdAt
)
