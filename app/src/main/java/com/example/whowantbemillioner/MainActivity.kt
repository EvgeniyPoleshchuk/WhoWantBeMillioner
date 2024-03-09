package com.example.whowantbemillioner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                    composable("ProgressScreen" + "/{counter}" + "/{isChecked}") { navBackStack ->
                        val counter = navBackStack.arguments?.getString("counter")
                        val isChecked = navBackStack.arguments?.getString("counter").toBoolean()
                        ProgressScreen(
                            onClick = { navController.navigate("GameScreen") },
                            counter = counter?.toInt(),
                            isChecked = isChecked
                        )
                    }
                    composable("GameScreen") {
                        GameScreen(
                            onClick = {navController.navigate("MainScreen")},
                            EndGameScreen = {navController.navigate("EndScreen")},
                            navController = navController
                        )
                    }
                    composable("RulesScreen") {
                        RulesScreen {
                            navController.navigate("MainScreen")
                        }
                    }
                    composable("EndScreen") {
                        EndScreen(
                            navigateToMainScreen = { navController.navigate("MainScreen") },
                            navigateToGameScreen = { navController.navigate("GameScreen") },

                        )
                    }

                }


            }

        }
    }
}
