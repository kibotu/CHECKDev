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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Repository(
    private val api: Api,
    private val answerDao: AnswerDao,
    private val roleDao: RoleDao,
    private val scenarioDao: ScenarioDao,
    private val phaseDao: PhaseDao,
    private val actionDao: ActionDao
) {

    init {
        MainScope()
            .launch {
                getRoles().collect {
                    if (it.isEmpty()) {
                        addDummyRoles()
                    }
                }
            }
    }

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

    suspend fun addAction(action: ActionEntity) {
        actionDao.upsert(action)
    }

    private suspend fun addDummyRoles() {
        RoleEntity(
            id = "CEO",
            model = "llama3",
            ip = "bla",
            bias = "world conqueror",
            icon = "bla",
            role = "CEO",
            temperature = "0.7"
        ).let { addRole(it) }

        RoleEntity(
            id = "PM",
            model = "llama3",
            ip = "bla",
            bias = "world conquerors helper",
            icon = "bla",
            role = "PM",
            temperature = "0.2"
        ).let { addRole(it) }

        RoleEntity(
            id = "Programmer",
            model = "llama3",
            ip = "bla",
            bias = "nerdy worker",
            icon = "bla",
            role = "PROGRAMMER",
            temperature = "0.9"
        ).let { addRole(it) }

        RoleEntity(
            id = "QA",
            model = "llama3",
            ip = "bla",
            bias = "picky tester",
            icon = "bla",
            role = "QA",
            temperature = "0.9"
        ).let { addRole(it) }
    }

    // endregion
}