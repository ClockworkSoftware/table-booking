package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.clockwork.tablebooking.R
import org.clockwork.tablebooking.ui.theme.AppTheme

@Composable
fun LogoMed(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.logo_medium), contentDescription = null,
        modifier = modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(240f / 51f)
    )
}

@Composable
fun LogoSmall(modifier: Modifier = Modifier) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(60),
        modifier = modifier
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val margin = maxWidth * 0.2f

            Image(
                painter = painterResource(R.drawable.logo_small), contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = margin, vertical = 10.dp)
                    .aspectRatio(58f / 51f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLogoSmall() {
    AppTheme {
        LogoSmall()
    }
}

@Composable
fun LogoutIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.logout_small), contentDescription = null,
        modifier = modifier
            .aspectRatio(42f / 42f)
    )
}

@Preview
@Composable
fun PreviewLogoutIcon() {
    AppTheme {
        LogoutIcon()
    }
}

@Composable
fun CrossSign(modifier: Modifier = Modifier, isCross: Boolean = true) {
    Image(
        painter = painterResource(R.drawable.x_sign), contentDescription = null,
        modifier = modifier
            .rotate(if (isCross) 0f else 45f)
    )
}

@Composable
fun BuildingIcon() {
    Image(
        painter = painterResource(R.drawable.building), contentDescription = null,
        modifier = Modifier.aspectRatio(1f)
    )
}

@Composable
fun CalendarIcon() {
    Image(
        painter = painterResource(R.drawable.calendar), contentDescription = null
    )
}

@Composable
fun ClockIcon() {
    Image(
        painter = painterResource(R.drawable.clock), contentDescription = null
    )
}