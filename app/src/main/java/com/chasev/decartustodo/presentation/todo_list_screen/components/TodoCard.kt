package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chasev.decartustodo.ui.theme.DecartusToDoTheme
import com.chasev.decartustodo.utils.dateToLocal
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoCard(
    isCompleteChecked: Boolean,
    isImportantChecked: Boolean,
    taskText: String,
    whenTask: Date? = null,
    onCheckedChanged: (Boolean) -> Unit,
    onImportantChanged: (Boolean) -> Unit,
    onCardClick: () -> Unit,
    onCardLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        modifier
            .fillMaxWidth(1f)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            RoundCheckbox(
                isChecked = isCompleteChecked,
                onCheckedChange = onCheckedChanged,
                size = 40.dp,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = taskText,
                maxLines = 2,
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxWidth()
                    .combinedClickable(onClick = onCardClick, onLongClick = onCardLongClick),
                textDecoration = if (isCompleteChecked) TextDecoration.LineThrough else TextDecoration.None
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 80.dp, max = 80.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StarCheckbox(
                    isChecked = isImportantChecked,
                    onCheckedChanged = onImportantChanged,
                    size = 40.dp,
                )
                if (whenTask != null) {
                    Text(text = dateToLocal(whenTask))
                }
            }


        }
    }

}

@Preview()
@Composable
fun TodoCardPreview() {
    DecartusToDoTheme {
        TodoCard(
            isCompleteChecked = false,
            isImportantChecked = true,
            taskText = "Somesopsmsosd asdkaso asdklasdas jklghiopjt",
            whenTask = Date(),
            {}, {}, {}, {}
        )
    }

}