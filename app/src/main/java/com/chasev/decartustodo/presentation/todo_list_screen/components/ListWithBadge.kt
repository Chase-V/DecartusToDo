package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListWithBadge(
    badgeText: String,
//    itemsList:List<TodoTask>
) {
    Badge(text = badgeText)
    Spacer(modifier = Modifier.height(8.dp))
    /*LazyColumn() {
        items(itemsList){item->
            TodoCard(
                isCompleteChecked = item.isComplete,
                isImportantChecked = item.isImportant,
                taskText = item.taskText,
                onCheckedChanged = {  },
                onImportantChanged = { }
            )
        }

    }*/
}

@Preview
@Composable
private fun ListWithBadgePreview() {
    ListWithBadge(
        badgeText = "Сегодня"
    )
}

// TODO: список в lazyList