package com.forbes.cat.catalogue.ui.screens.root

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forbes.cat.catalogue.navigation.NavigationItem
import com.forbes.cat.catalogue.R
import com.forbes.cat.catalogue.ui.screens.CatScreen
import com.forbes.cat.catalogue.ui.screens.FavouritesScreen
import com.forbes.cat.catalogue.ui.screens.HomeScreen
import com.forbes.cat.catalogue.viewmodels.CatInfoViewModel
import com.forbes.cat.catalogue.viewmodels.CatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(context: Context) {
    val vm = CatViewModel()
    val vmc = CatInfoViewModel()
//    val fvm = FavouriteCatViewModel()
    val navigationController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(stringResource(id = R.string.title))
                },
                actions = {
                    IconButton(onClick = { navigationController.navigate(NavigationItem.Favourites.route) }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favourite List")
                    }
                }
            )
        },
    ) {
        NavHost(
            navController = navigationController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(NavigationItem.Home.route) {
                HomeScreen(vm = vm, navController = navigationController)
            }
            composable("CatInfo/{id}") {
                    navBackStackEntry ->
                /* Extracting the id from the route */
                val id = navBackStackEntry.arguments?.getString("id")
                /* We check if it's not null */
                id?.let { value ->
                    CatScreen(value, vm = vmc, context = context)
                }
            }
            composable(NavigationItem.Favourites.route){
                FavouritesScreen(context = context)
            }
        }
    }
}