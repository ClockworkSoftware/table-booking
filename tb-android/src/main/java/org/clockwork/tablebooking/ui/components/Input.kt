package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.clockwork.tablebooking.ui.theme.AppTheme

@Composable
fun SecureInputField(label: String, state: TextFieldState, enabled: Boolean = true) {
    OutlinedSecureTextField(
        label = { Text(label) },
        state = state,
        enabled = enabled,
        textObfuscationMode = TextObfuscationMode.RevealLastTyped,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RoundedSquareButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceBright,
    contentColor: Color = MaterialTheme.colorScheme.inverseSurface,
    enabled: Boolean = true,
    roundTopHalfOnly: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        shape = if (roundTopHalfOnly) RoundedCornerShape(10.dp, 10.dp)
            else RoundedCornerShape(10.dp),
        modifier = modifier,
        content = content
    )
}

@Preview
@Composable
fun PreviewRoundedSquareButton() {
    AppTheme {
        RoundedSquareButton {
            Text("Lorem ipsum dolor sit amet")
        }
    }
}