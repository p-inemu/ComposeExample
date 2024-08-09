package shared

import android.os.Bundle

typealias OnRouteResultHandler = (result: Bundle?) -> Unit

open class Navigation {

    private var navImpl: INavigation? = null
    fun setImpl(navImpl: INavigation? = null){
        this.navImpl = navImpl
    }
    var lastRoute: String? = null
        private set
    var lastArgs: Bundle? = null
        private set

    fun setResults(results: Bundle){
        val currentBackStackId = navImpl?.getCurrentBackStackEntryId() ?: return
        resultsList[currentBackStackId] = results
    }
    fun setResults(bundleBlock: Bundle.() -> Unit){
        val bundle = Bundle()
        bundleBlock(bundle)
        setResults(bundle)
    }
    private var resultsList = hashMapOf<String, Bundle>()

    fun onBackstackEntryClosed(entry: String){
        val results = resultsList.remove(entry)
        val resultsHandler = onResultsHandlers.firstOrNull { it.first == entry}
        resultsHandler?.second?.invoke(results)
    }

    private var onResultsHandlers = mutableListOf<Pair<String, OnRouteResultHandler>>()
    fun to(route: String, args: Bundle? = null, onResult: OnRouteResultHandler? = null){
        navImpl?.routeTo(route, args)
        val newBackStackId = navImpl?.getCurrentBackStackEntryId() ?: return

        lastRoute = route
        lastArgs = args

        onResultsHandlers.removeAll { it.first == newBackStackId }
        if(onResult != null){
            onResultsHandlers.add(
                Pair(
                    newBackStackId,
                    onResult
                )
            )
        }
    }
    fun back(){
        navImpl?.routeBack()
    }
}

interface INavigation{
    fun routeBack()
    fun routeTo(route: String, args: Bundle?)
    fun getCurrentBackStackEntryId(): String?
}
