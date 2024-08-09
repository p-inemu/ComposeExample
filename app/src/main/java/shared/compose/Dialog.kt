package shared.compose

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    bgColor: Color = Color.White,
    radius: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier
            .background(
                bgColor,
                RoundedCornerShape(radius, radius, 0.dp, 0.dp)
            )
            .wrapContentHeight()
            .fillMaxWidth()
    ){
        content()
    }
}

fun NavGraphBuilder.dialogBottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    ),
    bgColor: Color = Color.White,
    radius: Dp = 20.dp,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(route, arguments, deepLinks, dialogProperties){ navBackStackEntry ->
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        BottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
            bgColor = bgColor,
            radius = radius,
        ){
            content(navBackStackEntry)
        }
    }
}

fun NavGraphBuilder.dialogCenterSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    bgColor: Color = Color.White,
    radius: Dp = 20.dp,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(route, arguments, deepLinks, dialogProperties){ navBackStackEntry ->
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.CENTER)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(radius))
                .background(bgColor)
        ){
            content(navBackStackEntry)
        }
    }
}

