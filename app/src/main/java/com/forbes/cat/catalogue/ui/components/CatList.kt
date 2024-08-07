package com.forbes.cat.catalogue.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.forbes.cat.catalogue.viewmodels.CatViewModel

@Composable
fun CatList(innerPadding: PaddingValues, vm: CatViewModel, navController: NavHostController) {
    LaunchedEffect(Unit, block = {
        vm.getCatImageList()
    })
    val listState = rememberLazyListState()
    val isAtBottom = remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == vm.imageList.lastIndex
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(state=listState, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                items(vm.imageList) { cat ->
                    CatListItem(image = cat.url, name = cat.breeds[0].name, id = cat.id, navController)
                }
            }
            if (isAtBottom.value) {
                vm.catListComplete = false
            }
        }
    }
}