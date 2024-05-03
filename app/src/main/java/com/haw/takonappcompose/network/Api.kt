package com.haw.takonappcompose.network

import com.haw.takonappcompose.models.ChatAnswer
import com.haw.takonappcompose.models.PromptAnswer
import com.haw.takonappcompose.models.Question
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface Api {

    @POST("api/generate")
    @Headers("Content-Type: application/json")
    suspend fun prompt(
        @Body question: Question
    ): PromptAnswer

    @POST("api/chat")
    @Headers("Content-Type: application/json")
    suspend fun chat(
        @Body question: Question
    ): ChatAnswer


    @Streaming
    @POST("api/chat")
    @Headers("Content-Type: application/json")
    suspend fun chatStreamed(@Body question: Question)
}
