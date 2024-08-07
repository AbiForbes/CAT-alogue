package com.forbes.cat.catalogue.navigation

enum class Screen {
    HOME,
    FAVOURITES,
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Favourites : NavigationItem(Screen.FAVOURITES.name)
}