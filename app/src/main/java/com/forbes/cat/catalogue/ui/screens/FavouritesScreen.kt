package com.forbes.cat.catalogue.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FavouritesScreen(context: Context){
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("favourite_storage", Context.MODE_PRIVATE)
    val stringList = mutableStateListOf<String>()

    CoroutineScope(Dispatchers.Main).launch {
        val json = sharedPreferences.getString("favourites", "[]")
        if(json !== "[]") {
            stringList.addAll(gson.fromJson(json, Array<String>::class.java).toList())
        }
    }
    Column() {
        Text(text = "Favourites", style = MaterialTheme.typography.titleLarge)
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier) {
            items(stringList) { string ->
                Text(text = string)
            }
        }
    }

}