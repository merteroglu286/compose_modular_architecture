package com.merteroglu286.navigator.destinations

const val SIGNUP_ROUTE = "SignUpRoute"

class SignUpDestination : NavigationDestination {
    override fun route(): String = Screens.SignUpScreenRoute.route
}