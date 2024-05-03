package com.haw.takonappcompose.overseer

import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import timber.log.Timber

class Overseer(private val repository: Repository) {

    suspend fun ScenarioEntity.run() {
        val phases = repository.getPhasesById(id)
        for (phase in phases) {
            phase.run()
        }
    }

    private suspend fun PhaseEntity.run() {
        val actions = repository.getActionsByPhaseId(id)
        for (action in actions) {
            action.run()
        }
    }

    private suspend fun ActionEntity.run() {
        val role = repository.getRoleById(roleId)

        if (role == null) {
            Timber.w("no role for $this")
            return
        }


    }
}
