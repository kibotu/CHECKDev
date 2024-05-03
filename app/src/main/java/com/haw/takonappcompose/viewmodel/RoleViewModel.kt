package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haw.takonappcompose.database.AnswerEntity
import com.haw.takonappcompose.database.AppDatabase
import com.haw.takonappcompose.models.Message
import com.haw.takonappcompose.models.Resource
import com.haw.takonappcompose.models.Role
import com.haw.takonappcompose.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoleViewModel : ViewModel(), KoinComponent {

    private val database: AppDatabase by inject()
    private val repository: Repository by inject()

    private val _roles: MutableStateFlow<List<Role>> = MutableStateFlow(emptyList())
    val roles = _roles.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {

        viewModelScope.launch {
            _roles.update {
                (1..10).map {
                    Role(
                        model = "model #$it",
                        ip = "",
                        icon = "",
                        bias = "bias of model #$it",
                        role = "role $it"
                    )
                }
            }
//            repository.getRoles().collect { data ->
//                _roles.update { data }
//            }
        }

    }

    fun addRole() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addRole()
            }
        }
    }

}