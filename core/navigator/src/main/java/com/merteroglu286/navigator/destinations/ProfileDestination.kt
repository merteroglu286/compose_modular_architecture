package com.merteroglu286.navigator.destinations

import androidx.navigation.NamedNavArgument
import com.merteroglu286.navigator.routes.Routes

const val PROFILE_ROUTE = "ProfileRoute"

object ProfileDestination : NavigationDestination {
    override fun route(): String = Routes.ProfileScreenRoute.route

    override val arguments: List<NamedNavArgument>
        get() = listOf() // pass any argument needed
}