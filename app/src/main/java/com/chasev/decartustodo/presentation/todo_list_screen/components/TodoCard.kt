package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chasev.decartustodo.ui.theme.DecartusToDoTheme

@Composable
fun TodoCard(
    isCompleteChecked: Boolean,
    isImportantChecked: Boolean,
    taskText: String,
    onCheckedChanged: (Boolean) -> Unit,
    onImportantChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        modifier
            .fillMaxWidth(1f)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
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
            Text(text = taskText, maxLines = 2, modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.width(8.dp))
            StarCheckbox(
                isChecked = isImportantChecked,
                onCheckedChanged = onImportantChanged,
                size = 40.dp,
            )

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
            {}, {}
        )
    }

}