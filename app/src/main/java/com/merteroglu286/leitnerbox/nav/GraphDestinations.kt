package com.merteroglu286.leitnerbox.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.merteroglu286.auth.presentation.login.view.LoginScreen
import com.merteroglu286.home.HomeScreen
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.destinations.LoginDestination
import com.merteroglu286.navigator.destinations.NavigationDestination
import com.merteroglu286.navigator.destinations.SignUpDestination
import com.merteroglu286.signup.SignUpScreen

private val composableDestinations: Map<NavigationDestination, @Composable (AppNavigator, NavHostController) -> Unit> =
    mapOf(
        SignUpDestination() to { _, _ -> SignUpScreen() },
        HomeDestination to { _, navHostController -> HomeScreen(navHostController) },
        LoginDestination() to { appNavigator, _ -> LoginScreen(appNavigator = appNavigator) }
    )


fun NavGraphBuilder.addComposableDestinations(
    appNavigator: AppNavigator,
    navHostController: NavHostController
) {

    composableDestinations.forEach { entry ->
        val destination = entry.key
        composable(
            route = destination.route(),
            arguments = destination.arguments,
            deepLinks = destination.deepLinks
        ) {
            entry.value(appNavigator, navHostController)
        }
    }

}