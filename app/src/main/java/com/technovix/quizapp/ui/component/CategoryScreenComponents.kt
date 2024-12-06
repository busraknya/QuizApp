package com.technovix.quizapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technovix.quizapp.ui.navigation.NavigationScreens
import com.technovix.quizapp.ui.theme.Color1DarkRed
import com.technovix.quizapp.ui.theme.ColorPink
import com.technovix.quizapp.ui.viewmodel.QuestionCategories

@Composable
fun CategoryContent(
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(QuestionCategories.entries) { category ->
            val allCategories = when(category) {
                QuestionCategories.WORLD -> Pair(QuestionCategories.WORLD, Icons.Default.Public)
                QuestionCategories.ANIMAL -> Pair(QuestionCategories.WORLD, Icons.Default.Pets)
                QuestionCategories.SCIENCE -> Pair(QuestionCategories.WORLD, Icons.Default.Science)
                QuestionCategories.SPORTS -> Pair(QuestionCategories.WORLD, Icons.Default.Sports)
                QuestionCategories.GAMES -> Pair(QuestionCategories.WORLD, Icons.Default.Games)
                QuestionCategories.MUSIC -> Pair(QuestionCategories.WORLD, Icons.Default.MusicNote)
            }

            CategoryContentItem(
                navController = navController,
                category = allCategories)
        }
    }
}


@Composable
fun CategoryContentItem(
    navController: NavController,
    category: Pair<QuestionCategories, ImageVector>
) {
    Card(
        onClick = {
            navController.navigate("${NavigationScreens.SCREEN_QUESTION.name}/${category.first.name}")
        },
        modifier = Modifier
            .size(200.dp)
            .padding(10.dp)
            .border(1.dp, ColorPink, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color1DarkRed),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = category.second,
                contentDescription = "Category Icon",
                tint = Color.White
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = category.first.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun CategoryContentItemPreview(
    navController: NavController= rememberNavController(),
    category: Pair<QuestionCategories, ImageVector> = Pair(QuestionCategories.WORLD, Icons.Default.Public)
){
    CategoryContentItem(
        navController, category
    )
}
