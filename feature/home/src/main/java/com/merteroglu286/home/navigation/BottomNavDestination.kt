package com.merteroglu286.home.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.merteroglu286.navigator.destinations.NavigationDestination

data class BottomNavDestination(
    val destination: NavigationDestination,
    val title: String,
    val icon: ImageVector
)