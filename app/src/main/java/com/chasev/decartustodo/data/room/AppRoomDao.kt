package com.chasev.decartustodo.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AppRoomDao {

    @Query("SELECT * FROM todoTasks WHERE isComplete = 0 AND isDeleted = 0 ")
    fun observeCurrentListOfTasks(): Flow<List<TodoTaskEntity>>

    @Query("SELECT * FROM todoTasks WHERE taskId = :taskId")
    fun observeTaskById(taskId: String): Flow<TodoTaskEntity?>

    @Query("SELECT * FROM todoTasks WHERE whenDate BETWEEN :startDate AND :endDate")
    fun getSortedListOfTasks(
        startDate: Date,
        endDate: Date
    ): Flow<List<TodoTaskEntity>>

    @Query("SELECT * FROM todoTasks WHERE isComplete = 1 OR isDeleted = 1")
    fun getCompletedAndDeleted():Flow<List<TodoTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoTaskEntity)

    @Update
    suspend fun updateTask(task: TodoTaskEntity)

    @Query("DELETE FROM todoTasks WHERE taskId = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    @Query("DELETE FROM todoTasks WHERE isComplete = 1 OR isDeleted = 1")
    suspend fun deleteCompletedAndDeleted()

    @Query("DELETE FROM todoTasks")
    suspend fun deleteTasks()

}