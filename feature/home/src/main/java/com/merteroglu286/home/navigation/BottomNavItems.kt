package com.merteroglu286.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import com.merteroglu286.navigator.destinations.HomeDestination
import com.merteroglu286.navigator.destinations.ProfileDestination
import com.merteroglu286.navigator.destinations.SearchDestination

val bottomNavItems = listOf(
    BottomNavDestination(
        destination = HomeDestination,
        title = "Home",
        icon = Icons.Default.Home
    ),
    BottomNavDestination(
        destination = SearchDestination,
        title = "Search",
        icon = Icons.Default.Search
    ),
    BottomNavDestination(
        destination = ProfileDestination,
        title = "Profile",
        icon = Icons.Default.Person
    )
)