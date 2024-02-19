package com.chasev.decartustodo.di

import androidx.room.Room
import com.chasev.decartustodo.data.room.AppRoomDao
import com.chasev.decartustodo.data.room.AppRoomDatabase
import com.chasev.decartustodo.data.room.RoomRepository
import com.chasev.decartustodo.data.room.RoomRepositoryImpl
import com.chasev.decartustodo.presentation.ArchiveScreen.ArchiveScreenViewModel
import com.chasev.decartustodo.presentation.edit_task_screen.EditTaskViewModel
import com.chasev.decartustodo.presentation.todo_list_screen.TodoListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<AppRoomDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppRoomDatabase::class.java,
            "appRoomDatabase"
        ).build()
    }

    single<AppRoomDao> {
        val database: AppRoomDatabase = get<AppRoomDatabase>()
        database.getRoomDao()
    }

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<RoomRepository> {
        RoomRepositoryImpl(
            get<AppRoomDao>(),
            get<CoroutineDispatcher>(named("ioDispatcher"))
        )
    }

    viewModel { TodoListViewModel(get<RoomRepository>()) }
    viewModel { EditTaskViewModel(get(), get<RoomRepository>()) }
    viewModel { ArchiveScreenViewModel(get<RoomRepository>()) }
}