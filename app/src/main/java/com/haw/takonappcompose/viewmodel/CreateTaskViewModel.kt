package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.overseer.Overseer
import com.haw.takonappcompose.presentation.model.PhasePresentationModel
import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import com.haw.takonappcompose.scenario.domain.usecase.GetScenarioUseCase
import com.haw.takonappcompose.uuid.UIDGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateTaskViewModel : ViewModel(), KoinComponent {

    private val repository: Repository by inject()

    private val useCase = GetScenarioUseCase(repository)

    val overseer: Overseer by inject()

    private val currentScenarioId = 1

    val currentPhases = MutableStateFlow<List<PhasePresentationModel>>(emptyList())

    init {
        createScenario()
        viewModelScope.launch {
            currentPhases.update {
                useCase(currentScenarioId)
            }
        }
    }

    fun createScenario() {
        val scenario = ScenarioEntity(id = currentScenarioId)
        viewModelScope.launch { repository.addScenario(scenario) }
    }

    fun runScenario(task: String) {
        viewModelScope.launch {
            val scenario = repository.getScenario(1)
            overseer.run(requireNotNull(scenario), task)
        }
    }

    fun appendPhase() {
        viewModelScope.launch {
            val newPhase = PhaseEntity(
                id = UIDGenerator.newUID(),
                scenarioId = currentScenarioId,
            )
            repository.addPhase(newPhase)
            repository.addAction(
                ActionEntity(
                    id = UIDGenerator.newUID(),
                    roleId = "",
                    input = null,
                    output = null,
                    phaseId = newPhase.id,
                ),
            )
            currentPhases.update {
                useCase(currentScenarioId)
            }
        }
    }

    fun updatePhase(roleEntity: RoleEntity, phaseId: Int) {
        viewModelScope.launch {
            currentPhases.update {
                useCase(currentScenarioId)
            }
//            repository.addPhase()
        }
    }
}
