package com.chasev.decartustodo.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chasev.decartustodo.R
import com.chasev.decartustodo.navigation.AppDestinationArgs.TASK_ID_ARG
import com.chasev.decartustodo.navigation.AppDestinationArgs.TITLE_ARG
import com.chasev.decartustodo.navigation.AppDestinationArgs.USER_MESSAGE_ARG
import com.chasev.decartustodo.presentation.ArchiveScreen.ArchiveScreen
import com.chasev.decartustodo.presentation.edit_task_screen.EditTaskScreen
import com.chasev.decartustodo.presentation.todo_list_screen.TodoListScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = AppDestinations.TODOTASK_LIST_ROUTE,
    navActions: TaskNavigationActions = remember(navController) {
        TaskNavigationActions(navController = navController)
    }
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(
            AppDestinations.TODOTASK_LIST_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 },
            )
        ) {
            TodoListScreen(
                addTask = {
                    navActions.navigateToAddEditTransaction(
                        R.string.add_transaction,
                        null
                    )
                },
                editTask = { transactionId ->
                    navActions.navigateToAddEditTransaction(
                        R.string.edit_transaction,
                        transactionId
                    )
                },
                onNavigateToArchivePressed = {
                    navActions.navigateToArchiveScreen()
                }
            )
        }

        composable(
            AppDestinations.ADD_EDIT_TRANSACTION_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(TASK_ID_ARG) { type = NavType.StringType; nullable = true }
            )
        ) { entry ->
            val transactionId = entry.arguments?.getString(TASK_ID_ARG)
            EditTaskScreen(
                onTaskUpdate = {
                    navActions.navigateToTaskList(
                        if (transactionId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                    )
                }
            )

        }

        composable(AppDestinations.ARCHIVE_SCREEN_ROUTE) {
            ArchiveScreen()
        }
    }
}

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3