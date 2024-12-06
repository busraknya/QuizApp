package com.technovix.quizapp.data.dependencyinjection

import com.technovix.quizapp.data.network.QuestionAPI
import com.technovix.quizapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object NetworkModule {

     @Singleton
     @Provides
     fun provideQuestionAPI() : QuestionAPI {
         return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(QuestionAPI::class.java)
     }
 }