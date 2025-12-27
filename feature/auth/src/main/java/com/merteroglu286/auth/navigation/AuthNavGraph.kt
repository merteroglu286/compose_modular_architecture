package com.merteroglu286.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.merteroglu286.auth.presentation.login.view.LoginScreen
import com.merteroglu286.navigation.signUpNavGraph
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.LoginDestination
import com.merteroglu286.navigator.routes.Routes

fun NavGraphBuilder.authNavGraph(appNavigator: AppNavigator) {
    navigation(
        route = Routes.AuthGraph.route,
        startDestination = LoginDestination.route()
    ) {
        composable(
            route = LoginDestination.route(),
            arguments = LoginDestination.arguments,
            deepLinks = LoginDestination.deepLinks
        ) {
            LoginScreen(appNavigator = appNavigator)
        }

        signUpNavGraph(appNavigator = appNavigator)
    }
}