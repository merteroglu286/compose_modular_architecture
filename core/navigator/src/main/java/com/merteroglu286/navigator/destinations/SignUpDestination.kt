package com.merteroglu286.navigator.destinations

import com.merteroglu286.navigator.routes.Routes

const val SIGNUP_ROUTE = "SignUpRoute"

object SignUpDestination : NavigationDestination {
    override fun route(): String = Routes.SignUpScreenRoute.route
}