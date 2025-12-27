package com.merteroglu286.home.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.merteroglu286.home.presentation.home.view.HomeScreen
import com.merteroglu286.home.presentation.search.view.SearchScreen
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.destinations.SearchDestination
import com.merteroglu286.profile.navigation.profileNavGraph

@Composable
fun BottomNavHost(
    appNavigator: AppNavigator,
    rootNavController: NavHostController
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == item.destination.route()
                        } == true,
                        onClick = {
                            bottomNavController.navigate(item.destination.route()) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = HomeDestination.route(),
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(
                route = HomeDestination.route(),
                arguments = HomeDestination.arguments,
                deepLinks = HomeDestination.deepLinks
            ) {
                HomeScreen(rootNavController)
            }

            composable(
                route = SearchDestination.route(),
                arguments = SearchDestination.arguments,
                deepLinks = SearchDestination.deepLinks
            ) {
                SearchScreen()
            }

            profileNavGraph(appNavigator)
        }
    }
}