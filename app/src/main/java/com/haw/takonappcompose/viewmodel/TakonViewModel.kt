package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haw.takonappcompose.database.AnswerEntity
import com.haw.takonappcompose.database.AppDatabase
import com.haw.takonappcompose.models.Message
import com.haw.takonappcompose.models.Resource
import com.haw.takonappcompose.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TakonViewModel : ViewModel(), KoinComponent {

    private val database: AppDatabase by inject()
    private val repository: Repository by inject()

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {

        viewModelScope.launch {
            repository.getMessages().collect { data ->
                _messages.update { data }
            }
        }

    }

    fun askQuestion(question: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.answerDao().addAnswer(
                    answerEntity = AnswerEntity(
                        role = "user",
                        content = question
                    )
                )
            }
            _loading.update { true }
            repository.chat(
                prevQuestion = messages.value,
                question = question
            ).also { baseModel ->
                _loading.update { false }
                when (baseModel) {
                    is Resource.Success -> {
                        withContext(Dispatchers.IO) {
                            database.answerDao().addAnswer(
                                answerEntity = AnswerEntity(
                                    role = "assistant",
                                    content = baseModel.data.message?.content
                                )
                            )
                        }
                    }

                    is Resource.Error -> {
                        println("Something wrong : ${baseModel.error}")
                    }

                    else -> {}
                }
            }
        }
    }

    fun clear() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clear()
            }
        }
    }

}