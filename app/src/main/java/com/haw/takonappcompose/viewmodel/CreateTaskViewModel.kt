package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.overseer.Overseer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateTaskViewModel : ViewModel(), KoinComponent {

    val overseer: Overseer by inject()

    fun addPhaseToScenarioEnd() {}

    fun updatePhase(roleEntity: RoleEntity, int: Int) {
    }
}
