package shared.compose

import android.os.Build
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun setStatusBarLightFg(light: Boolean = true) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            view.context.getActivity()?.window?.let { window ->
                WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !light
            }
        }
    }
}

@Composable
fun setNavBarLightFg(light: Boolean = true){
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            view.context.getActivity()?.window?.let { window ->
                WindowCompat.getInsetsController(
                    window,
                    window.decorView
                ).isAppearanceLightNavigationBars = !light
                if (!light && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }
}