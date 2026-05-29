package org.clockwork.tablebooking.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineBreak.Strategy
import androidx.compose.ui.text.style.LineBreak.Strictness
import androidx.compose.ui.text.style.LineBreak.WordBreak
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.clockwork.tablebooking.R

val JostFontFamily = FontFamily(
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_italic, FontWeight.Light)
)

private fun jostTextStyle(config: TextStyle.() -> TextStyle = { this }) : TextStyle {
    return TextStyle(fontFamily = JostFontFamily).config()
}

val AppTypography = Typography(
    displayLarge = jostTextStyle {
        copy(
            fontSize = 40.sp,
            letterSpacing = 7.sp,
            lineHeight = 1.25.em,
            lineBreak = LineBreak(
                strategy = Strategy.Simple,
                strictness = Strictness.Normal,
                wordBreak = WordBreak.Default
            )
        )
    },
    displayMedium = jostTextStyle {
        copy(
            fontSize = 35.sp,
            letterSpacing = 3.sp,
            lineHeight = 1.25.em,
            lineBreak = LineBreak(
                strategy = Strategy.Simple,
                strictness = Strictness.Normal,
                wordBreak = WordBreak.Default
            )
        )
    },
    bodyMedium = jostTextStyle()
)
