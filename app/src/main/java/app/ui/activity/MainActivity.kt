package app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.AppNavigation
import app.ui.screen.mainScreen.MainScreen
import app.ui.screen.textInput.TextInputScreen
import app.ui.screen.textInput.TextInputViewModel
import dagger.hilt.android.AndroidEntryPoint
import shared.INavigation
import shared.compose.dialogBottomSheet
import shared.compose.navigate
import shared.onScreenClosed
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var appNavigation: AppNavigation
    private var navHostController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navHostController = rememberNavController()
            remember {
                appNavigation.setImpl( object : INavigation {
                    override fun routeBack() {
                        navHostController?.popBackStack()
                    }
                    override fun routeTo(route: String, args: Bundle?) {
                        navHostController?.navigate(route, args)
                    }
                    override fun getCurrentBackStackEntryId(): String? {
                        return navHostController?.currentBackStackEntry?.id
                    }
                })
                navHostController?.onScreenClosed {
                    appNavigation.onBackstackEntryClosed(it)
                }
                true
            }

            Nav(navHostController!!)
        }
    }

    override fun onDestroy() {
        appNavigation.setImpl(null)
        super.onDestroy()
    }
}

@Composable
fun Nav(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = "main"
) {
    NavHost(navHostController, startDestination = startDestination) {
        composable("main") {
            MainScreen()
        }

        dialogBottomSheet("inputText"){
            val viewModel: TextInputViewModel = hiltViewModel()

            remember(viewModel) {
                val text = it.arguments?.getString("text") ?: ""
                viewModel.init(text)
                true
            }

            TextInputScreen(viewModel)
        }
    }
}