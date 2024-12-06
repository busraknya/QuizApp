package com.technovix.quizapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technovix.quizapp.ui.theme.Color1Blue

@Composable
fun ScoreBar(
    correctAnswerCount: Int,
    wrongAnswerCount: Int,
) {
    val correctScore = remember(correctAnswerCount) { correctAnswerCount }
    val wrongScore = remember(wrongAnswerCount) { wrongAnswerCount }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(34.dp)
            )
            .clip(RoundedCornerShape(50)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .background(Color.Green)
                    .padding(10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$correctScore",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .background(Color.Red.copy(0.5f))
                    .padding(10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "$wrongScore",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        }

    }
}

@Preview
@Composable
private fun ScoreBarPreview() {
    ScoreBar(correctAnswerCount = 10, wrongAnswerCount = 20)
}

@Composable
fun QuestionTrackerBar(
    counter: Int,
    outOf: Int
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                ) {
                    append("Question ${counter +1} /")
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 17.sp
                        )
                    ) {
                        append("$outOf")
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun QuestionTrackerBarPreview() {
    QuestionTrackerBar(counter = 10, outOf = 100)
}

@Preview
@Composable
fun DrawDottedLine() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .padding(vertical = 5.dp)
    ) {
        drawLine(
            color = Color.White,
            start = Offset(0f,0f),
            end = Offset(size.width,0f),
            strokeWidth = 10f,
            pathEffect = pathEffect
        )
    }
}

@Composable
fun QuestionChoicesLayout(
    questionChoicesState: MutableList<String>,
    userAnswerState: MutableState<Int?>,
    answerAction: (Int) -> Unit,
    isNextButtonPassive: MutableState<Boolean>,
    correctAnswerState: MutableState<Boolean?>
) {
    questionChoicesState.forEachIndexed{index, s ->
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxSize()
                .wrapContentHeight()
                .height(45.dp)
                .border(
                    width = 2.dp,
                    color = Color1Blue,
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(50))
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            RadioButton(
                selected = (userAnswerState.value == index),
                onClick = {
                    answerAction.invoke(index)
                    isNextButtonPassive.value = true
                },
                enabled = !isNextButtonPassive.value,
                modifier = Modifier.padding(start = 15.dp),
                colors = RadioButtonDefaults.colors(
                    disabledSelectedColor = if((correctAnswerState.value == true) && (index == userAnswerState.value)) {
                        Color.Green.copy(0.2f)
                    } else {
                        Color.Red.copy(0.2f)
                    },
                    disabledUnselectedColor = Color1Blue,
                    unselectedColor = Color.White
                )
            )

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = if (correctAnswerState.value == true && index == userAnswerState.value) {
                            Color.Green
                        } else if (correctAnswerState.value == false && index == userAnswerState.value) {
                            Color.Red
                        } else {
                            Color.White
                        },
                        fontSize = 15.sp
                    )
                ) {
                    append(s)
                }
            }
            
            Text( annotatedString )
        }
    }
}

@Preview
@Composable
private fun QuestionChoicesLayoutPreview() {
    QuestionChoicesLayout(
        questionChoicesState = mutableListOf("A","B","C","D"),
        userAnswerState = remember { mutableStateOf(null) },
        answerAction = {},
        isNextButtonPassive = remember { mutableStateOf(false) },
        correctAnswerState = remember { mutableStateOf(false) }
    )
}