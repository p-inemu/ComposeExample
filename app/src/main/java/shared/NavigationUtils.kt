package shared

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavHostController.onScreenClosed(code: (entry: String) -> Unit){
    var prevEntry: String? = null
    var currentEntry: String? = null
    addOnDestinationChangedListener { controller, _, _ ->
        val newPrevEntry = controller.previousBackStackEntry?.id
        val newCurrentEntry = controller.currentBackStackEntry?.id

        val isBack = prevEntry != null && newCurrentEntry == prevEntry
        if(isBack){
            if(currentEntry != null)
                code.invoke(currentEntry!!)
        }
        prevEntry = newPrevEntry
        currentEntry = newCurrentEntry
    }
}

@SuppressLint("RestrictedApi")
fun NavController.navigate(
    route: String,
    args: Bundle?,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}