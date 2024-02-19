package com.chasev.decartustodo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.chasev.decartustodo.navigation.AppDestinationArgs.TASK_ID_ARG
import com.chasev.decartustodo.navigation.AppDestinationArgs.TITLE_ARG
import com.chasev.decartustodo.navigation.AppDestinationArgs.USER_MESSAGE_ARG
import com.chasev.decartustodo.navigation.AppScreens.ADD_EDIT_TASK_SCREEN
import com.chasev.decartustodo.navigation.AppScreens.ARCHIVE_SCREEN
import com.chasev.decartustodo.navigation.AppScreens.TODOTASK_LIST_SCREEN

private object AppScreens {
    const val TODOTASK_LIST_SCREEN = "tasksList"
    const val ADD_EDIT_TASK_SCREEN = "addEditTasksScreen"
    const val ARCHIVE_SCREEN = "archiveScreen"
}

object AppDestinations {
    const val TODOTASK_LIST_ROUTE =
        "$TODOTASK_LIST_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val ADD_EDIT_TRANSACTION_ROUTE =
        "$ADD_EDIT_TASK_SCREEN/{$TITLE_ARG}?$TASK_ID_ARG={$TASK_ID_ARG}"
    const val ARCHIVE_SCREEN_ROUTE = ARCHIVE_SCREEN
}

object AppDestinationArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

class TaskNavigationActions(private val navController: NavController) {
    fun navigateToTaskList(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            TODOTASK_LIST_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToAddEditTransaction(title: Int, transactionId: String?) {
        navController.navigate(
            "$ADD_EDIT_TASK_SCREEN/$title".let {
                if (transactionId != null) "$it?$TASK_ID_ARG=$transactionId" else it
            }
        )
    }

    fun navigateToArchiveScreen(){
        navController.navigate(ARCHIVE_SCREEN)
    }
}