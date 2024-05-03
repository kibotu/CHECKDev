package com.haw.takonappcompose.repositories

import com.haw.takonappcompose.database.AnswerDao
import com.haw.takonappcompose.database.AnswerEntity
import com.haw.takonappcompose.database.RoleDao
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.models.ChatAnswer
import com.haw.takonappcompose.models.Message
import com.haw.takonappcompose.models.Question
import com.haw.takonappcompose.models.Resource
import com.haw.takonappcompose.network.Api
import com.haw.takonappcompose.scenario.datasources.db.ActionDao
import com.haw.takonappcompose.scenario.datasources.db.ActionEntity
import com.haw.takonappcompose.scenario.datasources.db.PhaseDao
import com.haw.takonappcompose.scenario.datasources.db.PhaseEntity
import com.haw.takonappcompose.scenario.datasources.db.ScenarioDao
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class Repository(
    private val api: Api,
    private val answerDao: AnswerDao,
    private val roleDao: RoleDao,
    private val scenarioDao: ScenarioDao,
    private val phaseDao: PhaseDao,
    private val actionDao: ActionDao
) {

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
        return answerDao.getAnswer().map { value ->
            value.map { entity ->
                Message(role = entity.role ?: "", content = entity.content ?: "")
            }
        }
    }

    suspend fun addAnswer(answer: Message) = withContext(Dispatchers.IO) {
        answerDao.addAnswer(AnswerEntity(role = answer.role, content = answer.content))
    }

    suspend fun clear() = withContext(Dispatchers.IO) {
        answerDao.clear()
    }

    suspend fun getRoles(): Flow<List<RoleEntity>> {
        return roleDao.getRoles()
    }

    suspend fun addRole(roleEntity: RoleEntity) = withContext(Dispatchers.IO) {
        roleDao.addRole(roleEntity)
    }

    // region scenarios

    suspend fun addScenario(scenario: ScenarioEntity) = withContext(Dispatchers.IO) {
        scenarioDao.upsert(scenario)
    }

    suspend fun addPhase(phase: PhaseEntity) = withContext(Dispatchers.IO) {
        val scenario = scenarioDao.getScenarioById(1) ?: ScenarioEntity(
            id = 1
        )
        scenarioDao.upsert(scenario)
        phase.scenarioId = scenario.id
        phaseDao.upsert(phase)
    }

    suspend fun addAction(action: ActionEntity) = withContext(Dispatchers.IO) {
        actionDao.upsert(action)
    }

    suspend fun getPhasesById(id: Int): List<PhaseEntity> = withContext(Dispatchers.IO) {
        phaseDao.getAll()?.filter { it.scenarioId == id }.orEmpty()
    }

    suspend fun getActionsByPhaseId(id: Int): List<ActionEntity> = withContext(Dispatchers.IO) {
        actionDao.getAll()?.filter { it.phaseId == id }.orEmpty()
    }

    suspend fun getRoleById(id: String): RoleEntity? = withContext(Dispatchers.IO) {
        roleDao.getAll()?.firstOrNull { it.id == id }
    }

    // endregion
}