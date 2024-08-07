package com.forbes.cat.catalogue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.forbes.cat.catalogue.ui.screens.root.RootScreen
import com.forbes.cat.catalogue.ui.theme.CATalogueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CATalogueTheme {
                RootScreen(applicationContext)
            }
        }
    }
}
