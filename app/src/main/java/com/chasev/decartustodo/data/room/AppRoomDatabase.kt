package com.chasev.decartustodo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import java.util.Date

@Database(entities = [TodoTaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): AppRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "AppDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}

object Converters {
    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time
}