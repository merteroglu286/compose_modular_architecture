package com.merteroglu286.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.routes.Routes
import com.merteroglu286.navigator.destinations.SignUpDestination
import com.merteroglu286.signup.SignUpScreen

fun NavGraphBuilder.signUpNavGraph(appNavigator: AppNavigator) {
    navigation(
        route = Routes.SignUpGraph.route,
        startDestination = SignUpDestination.route()
    ) {
        composable(
            route = SignUpDestination.route(),
            arguments = SignUpDestination.arguments,
            deepLinks = SignUpDestination.deepLinks
        ) {
            SignUpScreen()
        }
    }
}