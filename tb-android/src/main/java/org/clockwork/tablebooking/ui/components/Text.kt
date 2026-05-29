package org.clockwork.tablebooking.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.clockwork.tablebooking.ui.theme.AppTheme

@Composable
fun TitleText(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SubtitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun TitleTextExample() {
    AppTheme(darkTheme = false) {
        TitleText("Lorem ipsum dolor sit amet")
    }
}