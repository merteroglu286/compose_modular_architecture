package com.merteroglu286.leitnerbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.merteroglu286.leitnerbox.nav.addComposableDestinations
import com.merteroglu286.leitnerbox.ui.theme.LeitnerBoxTheme
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.Screens
import com.merteroglu286.navigator.event.NavigatorEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutingActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LeitnerBoxTheme {
                AppScaffold(appNavigator)

            }
        }
    }

    @Composable
    fun AppScaffold(appNavigator: AppNavigator) {
        val navController = rememberNavController()

        LaunchedEffect(navController) {
            appNavigator.destinations.collect { event ->

                when (event) {
                    is NavigatorEvent.Directions -> {
                        navController.navigate(
                            route = event.destination, builder = event.builder
                        )
                    }

                    NavigatorEvent.NavigateUp -> {
                        navController.navigateUp()
                    }

                    NavigatorEvent.PopBackStack -> {
                        navController.popBackStack()
                    }
                }

            }

        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Screens.LoginScreenRoute.route,
//                enterTransition = { fadeIn(animationSpec = tween(500)) },
//                exitTransition = { fadeOut(animationSpec = tween(500)) }
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                addComposableDestinations(appNavigator, navController)
            }
        }
    }
}