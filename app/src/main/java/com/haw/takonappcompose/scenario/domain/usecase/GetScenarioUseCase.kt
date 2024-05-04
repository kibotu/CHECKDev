package com.haw.takonappcompose.scenario.domain.usecase

import com.haw.takonappcompose.presentation.model.Action
import com.haw.takonappcompose.presentation.model.PhasePresentationModel
import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetScenarioUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(
        scenarioId: Int,
    ): List<PhasePresentationModel> =
        withContext(Dispatchers.IO) {
            val phases = repository.getPhasesById(scenarioId)
            val phasesParsed = mutableListOf<PhasePresentationModel>()
            for (phase in phases) {
                val phaseActions = repository.getActionsByPhaseId(phase.id)
                phasesParsed.add(
                    PhasePresentationModel(
                        actions = phaseActions.toAction(),
                        phaseId = phase.id,
                    ),
                )
            }
            return@withContext phasesParsed
        }

    private suspend fun List<ActionEntity>.toAction(): List<Action> = mapNotNull { actionEntity ->
        Action(role = repository.getRoleById(actionEntity.roleId))
    }
}
