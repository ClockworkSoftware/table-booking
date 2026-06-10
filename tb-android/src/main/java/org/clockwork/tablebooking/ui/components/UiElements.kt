package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.clockwork.tablebooking.ui.theme.AppTheme

@Composable
fun PlusExpandButton(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    isButtonCross: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(modifier) {
        Surface(
            shape = CircleShape,
            onClick = onClick,
            modifier = imageModifier
                .aspectRatio(1f)
        ) {
            Box {
                CrossSign(
                    isCross = isButtonCross,
                    modifier = Modifier
                        .matchParentSize()
                        .scale(0.7f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewXExpandButton() {
    AppTheme {
        PlusExpandButton()
    }
}

@Preview
@Composable
fun PreviewSmallXExpandButton() {
    AppTheme {
        PlusExpandButton(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewPlusExpandButton() {
    AppTheme {
        PlusExpandButton(isButtonCross = true)
    }
}

@Composable
fun RowWithExpandButton(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    isCrossButton: Boolean,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .background(color = Color.Transparent)
            .fillMaxWidth()
    ) {
        val (textRef, imageRef) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(textRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
        ) {
            content()
        }

        PlusExpandButton(
            modifier = buttonModifier
                .constrainAs(imageRef) {
                    top.linkTo(textRef.top, 2.dp)
                    bottom.linkTo(textRef.bottom, 2.dp)
                    end.linkTo(parent.end, 5.dp)
                    height = Dimension.fillToConstraints
                },
            imageModifier = buttonModifier,
            isButtonCross = isCrossButton,
            onClick = onButtonClick
        )
    }
}

@Composable
fun ContentWithIcon(
    drawable: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(0.3f)
        ) {
            drawable()
        }

        content()
    }
}