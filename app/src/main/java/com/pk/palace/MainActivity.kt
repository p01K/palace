package com.pk.palace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pk.palace.repo.ExerciseRepositoryFirebase
import com.pk.palace.repo.ExerciseViewModelFactory
import com.pk.palace.repo.WorkoutRepositoryFirebase
import com.pk.palace.repo.WorkoutViewModelFactory
import com.pk.palace.ui.compose.ExerciseDetailScreen
import com.pk.palace.ui.compose.ExerciseListScreen
import com.pk.palace.ui.compose.WorkoutListScreen
import com.pk.palace.ui.theme.PalaceTheme
import com.pk.palace.ui.viewmodel.ExerciseViewModel
import com.pk.palace.ui.viewmodel.WorkoutViewModel


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
    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(
            repository = ExerciseRepositoryFirebase()
        )
    )
    val workoutViewModel: WorkoutViewModel = viewModel(
        factory = WorkoutViewModelFactory(
            repository = WorkoutRepositoryFirebase()
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Exercises") },
                    label = { Text("Exercises") },
                    selected = currentDestination?.hierarchy?.any { it.route == "exercise_list" } == true,
                    onClick = {
                        navController.navigate("exercise_list") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Workouts") },
                    label = { Text("Workouts") },
                    selected = currentDestination?.hierarchy?.any { it.route == "workouts" } == true,
                    onClick = {
                        navController.navigate("workout_list") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "exercise_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("exercise_list") {
                ExerciseListScreen(
                    exerciseViewModel,
                    onItemSelected = { exerciseId ->
                        navController.navigate("exercise_detail/$exerciseId")
                    }
                )
            }

            composable("workout_list") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    WorkoutListScreen(workoutViewModel, onItemSelected = { workoutId ->
                        navController.navigate("workout_detail/$workoutId")
                    })
                }
            }

            composable(
                route = "exercise_detail/{exerciseId}",
                arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId")!!
                ExerciseDetailScreen(exerciseId = exerciseId, exerciseViewModel)
            }
        }
    }
}


