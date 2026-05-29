package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.clockwork.tablebooking.R

@Composable
fun LogoBig(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.logo_medium), contentDescription = null,
        modifier = modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(240f / 51f)
    )
}