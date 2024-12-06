package com.technovix.quizapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technovix.quizapp.data.model.QuestionItem
import com.technovix.quizapp.ui.component.DrawDottedLine
import com.technovix.quizapp.ui.component.QuestionChoicesLayout
import com.technovix.quizapp.ui.component.QuestionTrackerBar
import com.technovix.quizapp.ui.component.ScoreBar
import com.technovix.quizapp.ui.theme.Color1DarkBlue
import com.technovix.quizapp.ui.theme.Color1Red
import com.technovix.quizapp.ui.theme.ColorPink
import com.technovix.quizapp.ui.viewmodel.QuestionViewModel
import com.technovix.quizapp.util.Resource

@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel,
    selectedCategory: String
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllQuestions(selectedCategory)
    }

    val state = viewModel.questionState.value
    val questionIndex = remember { mutableIntStateOf(0) }

    when(state) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color1DarkBlue),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Color.White,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }
        is Resource.Error -> {
            Log.e("QuestionScreen", "QuestionScreen: ${state.message}")
        }
        is Resource.Success -> {
            val question =
                try {
                state.data?.get(questionIndex.intValue)
            } catch (e: Exception) {
                null
                //Log.e("QuestionScreen", "QuestionScreen: ${e.message}")
            }

            if(state.data != null && question != null) {
                QuestionLayout(question, questionIndex, viewModel) {
                    questionIndex.intValue += 1
                }
            }
        }
    }
}

@Composable
fun QuestionLayout(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClicked: (Int) -> Unit
) {
    var totalQuestion by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        totalQuestion = viewModel.getTotalQuestionsCount()
    }

    val questionChoicesState = remember(question) { question.questionChoices.toMutableList() }

    val userAnswerState = remember(question) { mutableStateOf<Int?>(null) }

    val userCorrectAnswerCountState = remember(question) { mutableIntStateOf(0) }

    val userWrongAnswerCountState = remember { mutableIntStateOf(0) }

    val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }

    val answerAction:(Int) -> Unit = remember(question) {
        {
            userAnswerState.value = it
            correctAnswerState.value = questionChoicesState[it] == question.questionAnswer

            if(correctAnswerState.value == true) {
                userCorrectAnswerCountState.intValue += 1
            } else {
                userWrongAnswerCountState.intValue += 1
            }
        }
    }

    val isNextButtonPassive = remember(question){ mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color1DarkBlue
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            ScoreBar(
                userCorrectAnswerCountState.intValue,
                userWrongAnswerCountState.intValue
            )

            Spacer(modifier = Modifier.height(20.dp))

            QuestionTrackerBar(counter = questionIndex.value,
                outOf = totalQuestion
            )

            DrawDottedLine()

            Column {
                Text(
                    text = question.questionText,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Start),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23.sp
                )
                
                Spacer(modifier = Modifier.height(70.dp))
                
                QuestionChoicesLayout(
                    questionChoicesState,
                    userAnswerState,
                    answerAction,
                    isNextButtonPassive,
                    correctAnswerState
                )

                Spacer(modifier = Modifier.height(100.dp))

                IconButton(
                    onClick = {
                       onNextClicked(questionIndex.value)
                    },
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(2.dp, ColorPink, RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color1Red),
                    enabled = isNextButtonPassive.value
                ) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Default.ChevronRight ,
                        tint = Color.White,
                        contentDescription = "Next Button Icon")
                }
            }
        }
    }
}