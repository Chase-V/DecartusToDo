package com.chasev.decartustodo.data.room

import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.data.repository.WorkResult
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface RoomRepository {
    fun getTaskFlow(taskId: String): Flow<WorkResult<TodoTask?>>
    fun getListOfTasksFlow(): Flow<WorkResult<List<TodoTask>>>
    fun getSortedListOfTasks(startDate: Date, endDate: Date): Flow<WorkResult<List<TodoTask>>>

    suspend fun addTask(task: TodoTask)
    suspend fun editTask(task: TodoTask)
    suspend fun deleteTask(taskId: String)
}