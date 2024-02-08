package fr.mastersime.panshare.Setup


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.mastersime.panshare.Setup.Screen.PHOTO_SUMMURY_VIEW_ROUTE
import fr.mastersime.panshare.Setup.Screen.START_VIEW_ROUTE
import fr.mastersime.panshare.feature.CameraScreen.CameraScreen
import fr.mastersime.panshare.feature.SummuryPhoto.PhotoSummury

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = START_VIEW_ROUTE,
    ) {
        composable(
            route = START_VIEW_ROUTE,
        ) {
            CameraScreen(navController)
        }
        composable(
            route = "$PHOTO_SUMMURY_VIEW_ROUTE/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            PhotoSummury(type)
        }
    }
}
