package com.technovix.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.technovix.quizapp.ui.screen.QuestionCategoryScreen
import com.technovix.quizapp.ui.screen.QuestionScreen
import com.technovix.quizapp.ui.viewmodel.QuestionViewModel

@Composable
fun MainNavigation() {
    val viewModel: QuestionViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SCREEN_CATEGORY.name
    ) {
        composable(route = NavigationScreens.SCREEN_CATEGORY.name) {
            QuestionCategoryScreen(navController)
        }
        composable(route = "${NavigationScreens.SCREEN_QUESTION.name}/{selectedCategory}",
            arguments = listOf(navArgument("selectedCategory") {type = NavType.StringType})
        ) { navBackStackEntry ->
            QuestionScreen(
                viewModel = viewModel,
                selectedCategory = navBackStackEntry.arguments?.getString("selectedCategory")!!
            )
        }
    }
}