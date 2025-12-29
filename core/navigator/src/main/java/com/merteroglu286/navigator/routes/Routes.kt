package com.merteroglu286.navigator.routes

import com.merteroglu286.navigator.destinations.HOME_ROUTE
import com.merteroglu286.navigator.destinations.LOGIN_ROUTE
import com.merteroglu286.navigator.destinations.PROFILE_ROUTE
import com.merteroglu286.navigator.destinations.SEARCH_ROUTE
import com.merteroglu286.navigator.destinations.SIGNUP_ROUTE
import com.merteroglu286.navigator.destinations.USER_AGE
import com.merteroglu286.navigator.destinations.USER_PARAM
import com.merteroglu286.navigator.destinations.USER_USERNAME

/*
sealed class Screens(val route: String) {
    data object LoginScreenRoute : Screens(route = LOGIN_ROUTE)
    data object SignUpScreenRoute : Screens(route = SIGNUP_ROUTE)
    data object HomeScreenRoute :
        Screens(route = "$HOME_ROUTE/{$USER_PARAM}/{$USER_AGE}/{$USER_USERNAME}")
}*/

// Graph routes
const val AUTH_GRAPH = "auth_graph"
const val SIGNUP_GRAPH = "signup_graph"
const val HOME_GRAPH = "home_graph"
const val PROFILE_GRAPH = "profile_graph"

sealed class Routes(val route: String) {
    // Auth screens
    data object LoginScreenRoute : Routes(route = LOGIN_ROUTE)
    data object SignUpScreenRoute : Routes(route = SIGNUP_ROUTE)

    // Home screens
    data object HomeScreenRoute : Routes(route = "${HOME_ROUTE}/{${USER_PARAM}}/{${USER_AGE}}/{${USER_USERNAME}}")
    data object SearchScreenRoute : Routes(route = SEARCH_ROUTE)

    // Graph routes
    data object AuthGraph : Routes(route = AUTH_GRAPH)
    data object SignUpGraph : Routes(route = SIGNUP_GRAPH)
    data object HomeGraph : Routes(route = HOME_GRAPH)
    data object ProfileGraph : Routes(route = PROFILE_GRAPH)

    data object ProfileScreenRoute : Routes(route = PROFILE_ROUTE)
}