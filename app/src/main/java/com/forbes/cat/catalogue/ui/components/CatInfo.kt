package com.forbes.cat.catalogue.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.forbes.cat.catalogue.responses.IdResponse
import com.forbes.cat.catalogue.ui.theme.CATalogueTheme
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CatInfo(cat: IdResponse, context: Context){
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("favourite_storage", Context.MODE_PRIVATE)
    val stringList = mutableStateListOf<String>()

    // Load list from SharedPreferences
    CoroutineScope(Dispatchers.Main).launch {
        val json = sharedPreferences.getString("favourites", "[]")
        if(json !== "[]") {
            stringList.addAll(gson.fromJson(json, Array<String>::class.java).toList())
        }
    }

    fun saveList() {
        CoroutineScope(Dispatchers.Main).launch {
            val json = gson.toJson(stringList)
            sharedPreferences.edit().putString("favourites", json).apply()
        }
    }

    val uriHandler = LocalUriHandler.current
    CATalogueTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(8.dp)){
                Row(modifier = Modifier, horizontalArrangement = Arrangement.Start) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth(0.5F)
                        .aspectRatio(1F)){
                        AsyncImage(model = cat.url, contentDescription = cat.breeds[0].name, contentScale = ContentScale.Crop, modifier = Modifier.aspectRatio(1F))
                        if(stringList.contains(cat.breeds[0].name.lowercase())){
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourite", tint = Color.Red, modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .align(Alignment.BottomStart)
                                .clickable {
                                    stringList.remove(cat.breeds[0].name.lowercase())
                                    saveList()
                                })
                        } else {
                            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Not favourite", tint = Color.DarkGray, modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .align(Alignment.BottomStart)
                                .clickable {
                                    stringList.add(cat.breeds[0].name.lowercase())
                                    saveList()
                                })
                        }
                    }
                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Bottom) {
                        Row() {
                            Text(
                                text = cat.breeds[0].name,
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Text(text = cat.breeds[0].origin, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(text = "Traits: ", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(text=cat.breeds[0].temperament, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Text(text = "Description: ", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = cat.breeds[0].description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.padding(12.dp))
                Button(onClick = { uriHandler.openUri(cat.breeds[0].wikipedia_url)}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Find out more...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}