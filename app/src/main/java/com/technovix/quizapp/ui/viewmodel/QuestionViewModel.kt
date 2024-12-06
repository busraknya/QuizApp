package com.technovix.quizapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technovix.quizapp.data.model.Question
import com.technovix.quizapp.data.repository.QuestionRepository
import com.technovix.quizapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    val questionState = mutableStateOf<Resource<Question>>(Resource.Loading())

    fun getAllQuestions(selectedCategory: String) {
        questionState.value = Resource.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            questionState.value = when(selectedCategory) {
                QuestionCategories.WORLD.name -> questionRepository.getAllWorldQuestions()
                QuestionCategories.ANIMAL.name -> questionRepository.getAllAnimalQuestions()
                QuestionCategories.SCIENCE.name -> questionRepository.getAllScienceQuestions()
                QuestionCategories.SPORTS.name -> questionRepository.getAllSportQuestions()
                QuestionCategories.GAMES.name -> questionRepository.getAllVideoGamesQuestions()
                QuestionCategories.MUSIC.name -> questionRepository.getAllMusicQuestions()
                else -> {
                    Resource.Error(message = "Category Not Found!")
                }
            }
        }
    }

    fun getTotalQuestionsCount() : Int {
        return questionState.value.data?.toMutableStateList()?.size ?: 0
    }
}