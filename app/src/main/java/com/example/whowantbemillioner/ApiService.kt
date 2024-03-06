package com.example.whowantbemillioner

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder().baseUrl("https://engine.lifeis.porn/api/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val questionService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("millionaire.php?qType=4&count=5")
    suspend fun getQuestionEasy(): QuestionsResponseEasy

    @GET("millionaire.php?qType=2&count=5")
    suspend fun getQuestionMedium(): QuestionsResponseMedium

    @GET("millionaire.php?qType=3&count=5")
    suspend fun getQuestionHard(): QuestionsResponseHard
}

suspend fun getQuestionAll (): QuestionsAll {
    val questionsResponseEasy = questionService.getQuestionEasy()
    val questionsResponseMedium = questionService.getQuestionMedium()
    val questionsResponseHard = questionService.getQuestionHard()
    val questionsResponseAll = QuestionsAll(questionsResponseEasy,questionsResponseMedium,questionsResponseHard)
    return questionsResponseAll
}