package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow

@Composable
fun InputField(label: String, state: TextFieldState, enabled: Boolean = true) {
    OutlinedTextField(
        label = { Text(label) },
        state = state,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    )
}