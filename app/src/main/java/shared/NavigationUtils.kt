package shared

import androidx.navigation.NavHostController

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