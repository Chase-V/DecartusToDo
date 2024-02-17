package com.chasev.decartustodo.presentation.todo_list_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StarCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    isEnabled: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit,
    checkedColor: Color = MaterialTheme.colorScheme.onSurface,
    uncheckedColor: Color = MaterialTheme.colorScheme.surface,
    size: Dp,
) {

    val starColor by animateColorAsState(targetValue = if (isChecked) checkedColor else uncheckedColor)
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        modifier = Modifier
            .toggleable(
                value = isChecked,
                enabled = isEnabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChanged
            )
            .size(size),
        tint = starColor
    )
}

@Preview
@Composable
private fun StarCheckboxPreview() {
    val isChecked = remember {
        mutableStateOf(false)
    }
    StarCheckbox(
        isChecked = isChecked.value,
        isEnabled = true,
        onCheckedChanged = { isChecked.value = !isChecked.value },
        size = 50.dp
    )
}