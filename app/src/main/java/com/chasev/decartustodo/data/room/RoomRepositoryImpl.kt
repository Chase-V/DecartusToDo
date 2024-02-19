package com.chasev.decartustodo.data.room

import com.chasev.decartustodo.data.repository.TodoTask
import com.chasev.decartustodo.data.repository.WorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class RoomRepositoryImpl internal constructor(
    private val tasksDao: AppRoomDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoomRepository {

    override fun getTaskFlow(taskId: String): Flow<WorkResult<TodoTask?>> =
        tasksDao.observeTaskById(taskId).map { taskEntity ->
            WorkResult.Success(taskEntity?.toTodoTask())
        }


    override fun getListOfTasksFlow(): Flow<WorkResult<List<TodoTask>>> =
        tasksDao.observeCurrentListOfTasks().map {
            WorkResult.Success(
                it.map { todoListEntity ->
                    todoListEntity.toTodoTask()
                })
        }

    override fun getSortedListOfTasks(
        startDate: Date,
        endDate: Date
    ): Flow<WorkResult<List<TodoTask>>> =
        tasksDao.getSortedListOfTasks(startDate, endDate).map {
            WorkResult.Success(it.map { taskEntity ->
                taskEntity.toTodoTask()
            })
        }

    override fun getCompletedAndDeleted(): Flow<WorkResult<List<TodoTask>>> =
        tasksDao.getCompletedAndDeleted().map {
            WorkResult.Success(
                it.map { todoListEntity ->
                    todoListEntity.toTodoTask()
                })
        }


    override suspend fun addTask(task: TodoTask) {
        if (task.taskText.isNullOrEmpty()) {
            throw Exception("Введите текст заметки")
        } else {
            tasksDao.insertTask(task.toTodoTaskEntity())
        }
    }

    override suspend fun editTask(task: TodoTask) {
        tasksDao.updateTask(task.toTodoTaskEntity())
    }

    override suspend fun deleteTask(taskId: String) {
        tasksDao.deleteTaskById(taskId)
    }

    override suspend fun deleteAll() {
        tasksDao.deleteTasks()
    }

    override suspend fun deleteCompletedAndDeleted() {
        tasksDao.deleteCompletedAndDeleted()
    }
}