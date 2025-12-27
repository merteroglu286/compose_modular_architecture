package com.merteroglu286.navigator.destinations

import androidx.navigation.NamedNavArgument
import com.merteroglu286.navigator.routes.Routes

const val HOME_ROUTE = "HomeRoute"

const val USER_PARAM = "user"
const val USER_AGE = "age"
const val USER_USERNAME = "username"

object HomeDestination : NavigationDestination {

    override fun route(): String = Routes.HomeScreenRoute.route

    override val arguments: List<NamedNavArgument>
        get() = listOf()

}