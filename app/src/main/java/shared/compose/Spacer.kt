package shared.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


@Composable
fun ColumnScope.Spacer(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}
@Composable
fun RowScope.Spacer(size: Dp) {
    Spacer(modifier = Modifier.width(size))
}


@Composable
fun SpacerWidth(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}
@Composable
fun SpacerHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun ColumnScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}
@Composable
fun RowScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

enum class InsetDirection {
    start,
    top,
    end,
    bottom
}

@Composable
fun InsetSpacer(
    direction: InsetDirection,
) {
    val inset = WindowInsets.systemBars
    val modifier = remember(direction) {
        when(direction){
            InsetDirection.start -> Modifier.windowInsetsStartWidth(inset)
            InsetDirection.top -> Modifier.windowInsetsTopHeight(inset)
            InsetDirection.end -> Modifier.windowInsetsEndWidth(inset)
            InsetDirection.bottom -> Modifier.windowInsetsBottomHeight(inset)
        }
    }
    Spacer(modifier)
}