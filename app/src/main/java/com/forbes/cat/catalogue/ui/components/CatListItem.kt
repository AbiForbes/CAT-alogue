package com.forbes.cat.catalogue.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.forbes.cat.catalogue.ui.theme.CATalogueTheme

@Composable
fun CatListItem(image: String, name: String, id: String, navController: NavHostController) {
    CATalogueTheme {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(0.8F)
                .aspectRatio(1F)
                .clickable { navController.navigate("CatInfo/$id") }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize()
                )
                Box(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth() ){
                    Text(
                        text = name, modifier = Modifier.align(Alignment.BottomEnd), fontWeight = FontWeight(600)

                    )
                }
            }
        }
    }
}