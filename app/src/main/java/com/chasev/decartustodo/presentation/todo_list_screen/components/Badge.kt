package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chasev.decartustodo.ui.theme.White

@Composable
fun Badge(
    text: String
) {
    Box(
        modifier = Modifier
            .background(White, shape = RoundedCornerShape(100))
            .padding(horizontal = 16.dp, vertical = 2.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }

}

@Preview
@Composable
private fun BadgePreview() {
    Badge(text = "Все задачи")
}