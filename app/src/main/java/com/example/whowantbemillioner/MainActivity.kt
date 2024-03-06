package com.example.whowantbemillioner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whowantbemillioner.ui.theme.WhoWantBeMillionerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            WhoWantBeMillionerTheme {
                NavHost(
                    navController = navController,
                    startDestination = "MainScreen"
                ) {

                    composable("MainScreen") {
                        MainScreen(navController = navController)
                    }
                    composable("ProgressScreen") {
                        ProgressScreen {
                            navController.navigate("GameScreen")
                        }
                    }
                    composable("GameScreen") {
                        GameScreen {
                            navController.navigate("MainScreen")
                        }
                    }
                    composable("RulesScreen") {
                        RulesScreen {
                            navController.navigate("MainScreen")
                        }
                    }

                }


            }

        }
    }
}
