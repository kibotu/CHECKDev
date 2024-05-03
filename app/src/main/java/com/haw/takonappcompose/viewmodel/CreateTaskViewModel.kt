package com.haw.takonappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haw.takonappcompose.overseer.Overseer
import com.haw.takonappcompose.repositories.Repository
import com.haw.takonappcompose.scenario.datasources.db.ScenarioEntity
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateTaskViewModel : ViewModel(), KoinComponent {

    private val repository: Repository by inject()

    private val overseer: Overseer by inject()

    init {
        createScenario()
    }

    fun createScenario() {
        val scenario = ScenarioEntity(id = 1)
        viewModelScope.launch { repository.addScenario(scenario) }
    }

    fun runScenario(task: String) {
        viewModelScope.launch {
            val scenario = repository.getScenario(1)
            overseer.run(requireNotNull(scenario), task)
        }
    }
}
