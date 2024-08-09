package shared.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Modifier.clickableRipple(radius: Dp = Dp.Unspecified, color: Color = Color.Unspecified, onClick: () -> Unit): Modifier{
    return this.clickable(
        interactionSource = remember {
            MutableInteractionSource()
        },
        indication = rememberRipple(radius = radius, color = color),
        onClick = onClick
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.combinedClickableRipple(
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified,
    onClick: () -> Unit = { },
    onLongClick: () -> Unit = { },
    onDoubleClick: () -> Unit = { },
): Modifier{
    return this.combinedClickable (
        onClick = onClick,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
        interactionSource = remember {
            MutableInteractionSource()
        },
        indication = rememberRipple(radius = radius, color = color),
    )
}