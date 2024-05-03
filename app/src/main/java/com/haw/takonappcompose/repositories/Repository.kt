package com.haw.takonappcompose.repositories

import android.R.id.message
import com.haw.takonappcompose.database.AnswerDao
import com.haw.takonappcompose.database.AnswerEntity
import com.haw.takonappcompose.models.ChatAnswer
import com.haw.takonappcompose.models.Message
import com.haw.takonappcompose.models.Question
import com.haw.takonappcompose.models.Resource
import com.haw.takonappcompose.network.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.InputStream


class Repository(private val api: Api, private val dao: AnswerDao) {

    suspend fun chat(
        prevQuestion: List<Message>,
        question: String
    ): Resource<ChatAnswer> {
        try {
            api.chat(
                question = Question(
                    messages = prevQuestion + Message(
                        role = "user",
                        content = question
                    ),
                    model = "llama3",
                    stream = false
                )
            ).also { response ->
                return Resource.Success(data = response)
            }
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }

    suspend fun getMessages(): Flow<List<Message>> {
        return dao.getAnswer().map { value ->
            value.map { entity ->
                Message(role = entity.role ?: "", content = entity.content ?: "")
            }
        }
    }

    suspend fun addAnswer(answer: Message) {
        dao.addAnswer(AnswerEntity(role = answer.role, content = answer.content))
    }

    suspend fun clear() {
        dao.clear()
    }

    suspend fun addRole() {
        //dao.addRole()
    }

}