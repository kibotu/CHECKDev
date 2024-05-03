package com.haw.takonappcompose.overseer

import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import timber.log.Timber

class Overseer(private val repository: Repository) {

    suspend fun run(scenario: ScenarioEntity, task: String) {
        val phases = repository.getPhasesById(scenario.id)

        for (phase in phases) {
            phase.run(task)
        }
    }

    private suspend fun PhaseEntity.run(task: String) {
        val actions = repository.getActionsByPhaseId(id)
        for (action in actions) {
            action.run(task)
        }
    }

    private suspend fun ActionEntity.run(task: String) {
        val role = repository.getRoleById(roleId)

        if (role == null) {
            Timber.w("no role for $this")
            return
        }


    }
}
