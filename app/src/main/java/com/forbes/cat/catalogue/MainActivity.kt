package com.forbes.cat.catalogue

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.forbes.cat.catalogue.ui.theme.CATalogueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CATalogueTheme {
                RootScreen()
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

enum class Screen {
    HOME,
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() {
    val vm = CatViewModel()
    val vmc = CatInfoViewModel()
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
                    CatScreen(value, vm = vmc)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(vm: CatViewModel, navController: NavHostController) {

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
            }
        },
    ) { innerPadding ->
        InnerContent(innerPadding, vm, navController)
    }
}

@Composable
fun InnerContent(innerPadding: PaddingValues, vm: CatViewModel, navController: NavHostController) {
    LaunchedEffect(Unit, block = {
        vm.getCatImageList()
    })
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally) {
            items(vm.imageList){ cat ->
                Cat(image = cat.url, name = cat.breeds[0].name, id = cat.id, navController)
                Log.d("MainActivity", cat.toString())
            }
        }
    }
}

@Composable
fun Cat(image: String, name: String, id: String, navController: NavHostController) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CATalogueTheme {
        Greeting("Android")
    }
}

@Composable
fun CatScreen(value: String, vm : CatInfoViewModel) {
    CATalogueTheme {
        LaunchedEffect(Unit, block = {
            vm.getCatInfo(value)
        })
        val cat = vm.idList
        if(cat != null){
            CatInfo(cat = cat)
        }
    }
}

@Composable
fun CatInfo(cat: IdResponse){
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
                AsyncImage(model = cat.url, contentDescription = cat.breeds[0].name, contentScale = ContentScale.Crop, modifier = Modifier.aspectRatio(1F))}
                Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Bottom) {
                        Text(
                            text = cat.breeds[0].name,
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleLarge
                        )
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(text=cat.breeds[0].temperament, style = MaterialTheme.typography.bodyLarge)
                }
            }
                Spacer(modifier = Modifier.padding(12.dp))
            Text(text = cat.breeds[0].description, style = MaterialTheme.typography.bodyLarge)


            }
        }
    }
}