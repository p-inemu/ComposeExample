package shared.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color


@Composable
inline fun Circle(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    bg: Color = Color.White,
    content: @Composable BoxScope.() -> Unit = { },
){
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .background(bg, CircleShape)
            .aspectRatio(1f),
        content = content,
    )
}
@Composable
fun Circle(
    modifier: Modifier = Modifier,
    bg: Color = Color.White,
){
    Box(modifier
        .background(bg, CircleShape)
        .aspectRatio(1f)) {
    }
}

@Composable
fun ClickableCircle(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    bg: Color = Color.White,
    onClick: (() -> Unit)? = null,
    ripple: Boolean = true,
    content: @Composable BoxScope.() -> Unit = { },
){
    val _modifier = if(ripple){
        modifier
            .clip(CircleShape)
            .background(bg, CircleShape)
            .aspectRatio(1f)
            .clickableRipple { onClick?.invoke() }
    } else {
        val interactionSource = remember { MutableInteractionSource() }
        modifier
            .background(bg, CircleShape)
            .aspectRatio(1f)
            .clickable(interactionSource, null) { onClick?.invoke() }
    }
    Box(
        contentAlignment = contentAlignment,
        modifier = _modifier,
        content = content
    )
}