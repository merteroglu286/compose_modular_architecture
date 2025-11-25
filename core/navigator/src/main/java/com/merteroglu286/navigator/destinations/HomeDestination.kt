package com.merteroglu286.navigator.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val HOME_ROUTE = "HomeRoute"

const val USER_PARAM = "user"
const val USER_AGE = "age"
const val USER_USERNAME = "username"

object HomeDestination : NavigationDestination {

    fun createHome(user: String, age: Int, userName: String) : String =
        "$HOME_ROUTE/$user/$age/$userName"

    override fun route(): String = Screens.HomeScreenRoute.route

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(USER_PARAM) { type = NavType.StringType },
            navArgument(USER_AGE) { type = NavType.IntType },
            navArgument(USER_USERNAME) { type = NavType.StringType }
        )

}