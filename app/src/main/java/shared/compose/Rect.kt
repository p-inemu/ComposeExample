package shared.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
inline fun Rect(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    bg: Color = Color.White,
    radius: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit = { },
){
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .background(bg, RoundedCornerShape(radius)),
        content = content,
    )
}

@Composable
inline fun Rect(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    bg: Color = Color.White,
    shape: Shape,
    content: @Composable BoxScope.() -> Unit = { },
){
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .background(bg, shape),
        content = content,
    )
}

@Composable
fun Rect(
    modifier: Modifier = Modifier,
    bg: Color = Color.White,
    shape: Shape,
){
    Box(
        modifier = modifier
            .background(bg, shape),
    )
}

@Composable
fun Rect(
    modifier: Modifier = Modifier,
    bg: Color = Color.White,
    radius: Dp = 0.dp,
){
    Box(
        modifier = modifier
            .background(bg, RoundedCornerShape(radius)),
    )
}

@Composable
fun ClickableRect(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    bg: Color = Color.White,
    radius: Dp = 0.dp,
    onClick: (() -> Unit)? = null,
    ripple: Boolean = true,
    content: @Composable BoxScope.() -> Unit = { },
){
    val _modifier = if(ripple){
        modifier
            .background(bg, RoundedCornerShape(radius))
            .clickableRipple { onClick?.invoke() }
    } else {
        val interactionSource = remember { MutableInteractionSource() }
        modifier
            .background(bg, RoundedCornerShape(radius))
            .clickable(interactionSource, null) { onClick?.invoke() }
    }

    Box(
        contentAlignment = contentAlignment,
        modifier = _modifier,
        content = content
    )
}