package com.merteroglu286.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.routes.Routes

fun NavGraphBuilder.homeNavGraph(
    appNavigator: AppNavigator,
    rootNavController: NavHostController
) {
    navigation(
        route = Routes.HomeGraph.route,
        startDestination = HomeDestination.route()
    ) {
        composable(
            route = HomeDestination.route(),
            arguments = HomeDestination.arguments,
            deepLinks = HomeDestination.deepLinks
        ) {
            BottomNavHost(appNavigator, rootNavController)
        }
    }
}