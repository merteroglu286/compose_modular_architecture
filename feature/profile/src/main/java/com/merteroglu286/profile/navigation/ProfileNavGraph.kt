package com.merteroglu286.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.ProfileDestination
import com.merteroglu286.navigator.routes.Routes
import com.merteroglu286.profile.presentation.view.ProfileScreen

fun NavGraphBuilder.profileNavGraph(appNavigator: AppNavigator) {
    navigation(
        route = Routes.ProfileGraph.route,
        startDestination = ProfileDestination.route()
    ) {
        composable(
            route = ProfileDestination.route(),
            arguments = ProfileDestination.arguments,
            deepLinks = ProfileDestination.deepLinks
        ) {
            ProfileScreen(appNavigator = appNavigator)
        }
    }
}