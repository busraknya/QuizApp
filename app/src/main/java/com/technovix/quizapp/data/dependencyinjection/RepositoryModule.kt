package com.technovix.quizapp.data.dependencyinjection

import com.technovix.quizapp.data.network.QuestionAPI
import com.technovix.quizapp.data.repository.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideQuestionRepository(questionAPI: QuestionAPI) = QuestionRepository(questionAPI)
}