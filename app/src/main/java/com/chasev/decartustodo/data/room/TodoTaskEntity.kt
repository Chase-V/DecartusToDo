package com.chasev.decartustodo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chasev.decartustodo.data.repository.TodoTask
import java.sql.Timestamp
import java.util.UUID

@Entity(tableName = "todoTasks")
data class TodoTaskEntity(
    @PrimaryKey var taskId: String,
    var taskText: String,
    var isImportant: Boolean,
    var isComplete: Boolean,
    var whenDate: Timestamp,
    var createdAt: Timestamp
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
    whenDate = whenDate,
    createdAt = createdAt
)
