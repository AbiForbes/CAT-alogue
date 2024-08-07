package com.forbes.cat.catalogue.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.forbes.cat.catalogue.ui.components.CatList
import com.forbes.cat.catalogue.viewmodels.CatViewModel

@Composable
fun HomeScreen(vm: CatViewModel, navController: NavHostController) {

    Scaffold(
    ) { innerPadding ->
        CatList(innerPadding, vm, navController)
    }
}