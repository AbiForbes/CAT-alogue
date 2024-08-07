package com.forbes.cat.catalogue.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.forbes.cat.catalogue.ui.components.CatInfo
import com.forbes.cat.catalogue.ui.theme.CATalogueTheme
import com.forbes.cat.catalogue.viewmodels.CatInfoViewModel

@Composable
fun CatScreen(value: String, vm : CatInfoViewModel, context: Context) {
    CATalogueTheme {
        LaunchedEffect(Unit, block = {
            vm.getCatInfo(value)
        })
        val cat = vm.idList
        if(cat != null){
            CatInfo(cat = cat, context = context)
        }
    }
}