package com.merteroglu286.navigator.destinations

import androidx.navigation.NamedNavArgument
import com.merteroglu286.navigator.routes.Routes

const val SEARCH_ROUTE = "SearchRoute"

object SearchDestination : NavigationDestination {
    override fun route(): String = Routes.SearchScreenRoute.route

    override val arguments: List<NamedNavArgument>
        get() = listOf() // pass any argument needed
}