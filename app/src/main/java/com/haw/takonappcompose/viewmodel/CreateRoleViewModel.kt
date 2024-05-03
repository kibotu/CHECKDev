package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateRoleViewModel : ViewModel(), KoinComponent {

    private val repository: Repository by inject()

    fun addRole(roleEntity: RoleEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addRole(roleEntity)
            }
        }
    }

}