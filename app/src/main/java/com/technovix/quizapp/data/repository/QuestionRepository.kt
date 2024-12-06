package com.technovix.quizapp.data.repository

import com.technovix.quizapp.data.model.Question
import com.technovix.quizapp.data.network.QuestionAPI
import com.technovix.quizapp.util.Resource
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val questionAPI: QuestionAPI
) {
    suspend fun getAllWorldQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllWorldQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllWorldQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllAnimalQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllAnimalQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllAnimalQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllScienceQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllScienceQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllScienceQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllSportQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllSportQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllSportQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllVideoGamesQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllVideoGamesQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllVideoGamesQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllMusicQuestions(): Resource<Question> {
        return try {
            if (questionAPI.getAllMusicQuestions().isSuccessful) {
                Resource.Success(data = questionAPI.getAllMusicQuestions().body())
            } else {
                Resource.Error("Network Response Failed!")
            }
        }catch(e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}