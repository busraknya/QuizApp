package com.technovix.quizapp.data.model

import com.google.gson.annotations.SerializedName

data class QuestionItem(
    @SerializedName("answer")
    val questionAnswer: String,
    @SerializedName("category")
    val questionCategory: String,
    @SerializedName("choices")
    val questionChoices: List<String>,
    @SerializedName("question")
    val questionText: String
)