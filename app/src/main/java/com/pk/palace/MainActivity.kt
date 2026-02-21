package com.pk.palace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pk.palace.repo.ExerciseViewModelFactory
import com.pk.palace.repo.FirebaseExerciseRepository
import com.pk.palace.ui.ExerciseViewModel
import com.pk.palace.ui.compose.ExerciseDetailScreen
import com.pk.palace.ui.compose.ExerciseListScreen
import com.pk.palace.ui.theme.PalaceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PalaceTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(
            repository = FirebaseExerciseRepository()
        )
    )
    NavHost(
        navController = navController,
        startDestination = "exercise_list"
    ) {
        composable("exercise_list") {
            ExerciseListScreen(
                viewModel,
                onItemSelected = { exerciseId ->
                    navController.navigate("exercise_detail/$exerciseId")
                }
            )
        }

        composable(
            route = "exercise_detail/{exerciseId}",
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")!!
            ExerciseDetailScreen(exerciseId = exerciseId, viewModel)
        }
    }
}


