package com.haw.takonappcompose.overseer

import com.haw.takonappcompose.database.AnswerEntity
import com.haw.takonappcompose.models.ChatAnswer
import com.haw.takonappcompose.models.Message
import com.haw.takonappcompose.models.Question
import com.haw.takonappcompose.models.Resource
import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import timber.log.Timber

class Overseer(private val repository: Repository) {

    suspend fun run(scenario: ScenarioEntity, task: String) {
        val phases = repository.getPhasesById(scenario.id)

        var previousPhase = phases.first()
        var result = previousPhase.run(task)

        phases.drop(1).forEachIndexed { index, phaseEntity ->
            result = phaseEntity.run(result.message?.content ?: "no message")
        }
    }

    private suspend fun PhaseEntity.run(task: String): ChatAnswer {
        val actions = repository.getActionsByPhaseId(id)

        var action = actions.first()
        var result = action.run(task)

        actions
            .drop(1)
            .forEachIndexed { index, actionEntity ->
                result = actionEntity.run(result.message?.content ?: "no message")
            }

        return result
    }

    private suspend fun ActionEntity.run(input: String): ChatAnswer {
        val role = requireNotNull(repository.getRoleById(roleId))

        val result: ChatAnswer? = null
        try {
            val response = repository.chat(
                Question(
                    model = role.model,
                    messages = listOf(
                        Message(
                            role = role.role,
                            content = input
                        )
                    ),
                    stream = false
                )
            )

            // save answer
            if (response is Resource.Success) {
                repository.addAnswer(
                    answerEntity = AnswerEntity(
                        role = "assistant",
                        content = response.data.message?.content
                    )
                )

                return response.data
            }
        } catch (e: Exception) {
            Timber.e(e)
        }


        return requireNotNull(result)
    }
}
