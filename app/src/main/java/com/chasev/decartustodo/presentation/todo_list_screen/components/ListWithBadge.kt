package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chasev.decartustodo.data.repository.TodoTask
import java.sql.Timestamp
import java.util.Date

@Composable
fun ListWithBadge(
    badgeText: String,
    itemsList: List<TodoTask>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Badge(text = badgeText)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(itemsList) { item ->
                TodoCard(
                    isCompleteChecked = item.isComplete,
                    isImportantChecked = item.isImportant,
                    taskText = item.taskText,
                    onCheckedChanged = { },
                    onImportantChanged = { }
                )
            }

        }
    }

}

@Preview
@Composable
private fun ListWithBadgePreview() {
    val testList = listOf<TodoTask>(
        TodoTask(
            taskText = "asdklasdklasdkl; asdjnaas asd qweasx",
            isImportant = true,
            isComplete = false,
            whenDate = Timestamp(Date().time),
            createdAt = Timestamp(Date().time)
        ),
        TodoTask(
            taskText = "1236789hj 213lnkSXCHasdjh asdz",
            isImportant = false,
            isComplete = true,
            whenDate = Timestamp(Date().time),
            createdAt = Timestamp(Date().time)
        ),
        TodoTask(
            taskText = "lorem sadasd asdferf2",
            isImportant = true,
            isComplete = true,
            whenDate = Timestamp(Date().time),
            createdAt = Timestamp(Date().time)
        ),
    )
    ListWithBadge(
        badgeText = "Сегодня",
        itemsList = testList
    )
}