package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp
) {
    val checkboxColor: Color by animateColorAsState(targetValue = if (isChecked) checkedColor else uncheckedColor)

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape = CircleShape)
            .toggleable(
                value = isChecked,
                enabled = isEnabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .background(color = checkboxColor)
            .border(width = 3.dp, color = checkedColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = uncheckedColor
            )
        }

    }

}

@Preview
@Composable
private fun RoundCheckboxPreview() {
    val isChecked = remember {
        mutableStateOf(false)
    }
    RoundCheckbox(
        isChecked = isChecked.value,
        onCheckedChange = { isChecked.value = !isChecked.value },
        size = 50.dp
    )

}