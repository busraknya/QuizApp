package com.technovix.quizapp.data.network

import com.technovix.quizapp.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
    @GET("world.json")
    suspend fun getAllWorldQuestions() : Response<Question>

    @GET("animals.json")
    suspend fun getAllAnimalQuestions() : Response<Question>

    @GET("science-technology.json")
    suspend fun getAllScienceQuestions() : Response<Question>

    @GET("sports.json")
    suspend fun getAllSportQuestions() : Response<Question>

    @GET("video-games.json")
    suspend fun getAllVideoGamesQuestions() : Response<Question>

    @GET("music.json")
    suspend fun getAllMusicQuestions() : Response<Question>
}