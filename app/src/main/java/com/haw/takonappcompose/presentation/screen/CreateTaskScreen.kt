package com.haw.takonappcompose.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.haw.takonappcompose.R
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.presentation.model.Action
import com.haw.takonappcompose.presentation.model.PhasePresentationModel
import com.haw.takonappcompose.ui.theme.BluePrimary
import com.haw.takonappcompose.viewmodel.CreateTaskViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTaskScreen(
    navController: NavController,
    viewModel: CreateTaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(50.dp),
    ) {
        TaskUI {
            viewModel.runScenario(it)
        }
        ScenarioUI(
            availableRoles = viewModel.availableRoles.collectAsState(initial = emptyList()).value,
            phases = viewModel.currentPhases.collectAsState(initial = emptyList()).value,
            addPhase = viewModel::appendPhase,
            onUpdatePhase = viewModel::updatePhase,
        )
    }
}

@Composable
private fun TaskUI(onClick: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        var task by remember {
            mutableStateOf(
                "",
            )
        }
        TextField(value = task, onValueChange = { task = it })
        IconButton(onClick = { onClick(task) }) {
            Image(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = null,
                colorFilter = ColorFilter.tint(BluePrimary),
            )
        }
    }
}

@Composable
private fun ScenarioUI(
    availableRoles: List<RoleEntity>,
    modifier: Modifier = Modifier,
    phases: List<PhasePresentationModel>,
    addPhase: () -> Unit,
    onUpdatePhase: (RoleEntity, Int, Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        phases.forEachIndexed { index, phase ->
            Phase(
                actions = phase.actions,
                availableRoles = availableRoles,
                modifier = Modifier.background(
                    if (index % 2 == 0) Color.LightGray else Color.White,
                ),
                onUpdatePhase = { x, y -> onUpdatePhase(x, y, phase.phaseId) },
            )
            if (index != phases.lastIndex) {
                LongArrow(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
        AddPhase(
            addPhase = addPhase,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun AddPhase(
    modifier: Modifier = Modifier,
    addPhase: () -> Unit,
) {
    Icon(
        modifier = modifier
            .clickable { addPhase() }
            .size(40.dp),
        painter = painterResource(id = R.drawable.add_circle),
        contentDescription = null,
        tint = Color.Green,
    )
}

@Composable
private fun Phase(
    availableRoles: List<RoleEntity>,
    actions: List<Action>,
    modifier: Modifier = Modifier,
    onUpdatePhase: (RoleEntity, Int) -> Unit,
) {
    Column(
        modifier = modifier
            .border(width = 1.dp, color = Color.Black)
            .padding(5.dp),
    ) {
        actions.forEachIndexed { index, action ->
            SelectRole(
                availableRoles = availableRoles,
                preSelected = action.role,
                onSelect = { onUpdatePhase(it, index) },
            )
            if (index != actions.lastIndex) {
                Arrow(
                    Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Composable
fun SimplePhase(
    availableRoles: List<RoleEntity>,
    modifier: Modifier = Modifier,
    role1: RoleEntity? = null,
    role2: RoleEntity? = null,
) {
    var agent1: RoleEntity? by remember {
        mutableStateOf(
            role1,
        )
    }
    var agent2: RoleEntity? by remember {
        mutableStateOf(
            role2,
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp, max = 200.dp),
    ) {
        SelectRole(
            availableRoles = availableRoles,
            onSelect = { agent1 = it },
        )
        Arrow(
            Modifier
                .rotate(90F)
                .align(Alignment.CenterHorizontally),
        )
        SelectRole(
            availableRoles = availableRoles,
            onSelect = { agent2 = it },
        )
    }
}

@Composable
private fun Arrow(
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier
            .widthIn(min = 20.dp)
            .rotate(90f),
        painter = painterResource(id = R.drawable.arrow_right),
        contentDescription = null,
    )
}

@Composable
private fun LongArrow(
    modifier: Modifier = Modifier,
) {
    Arrow(
        modifier = modifier.scale(2f),
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SelectRole(
    modifier: Modifier = Modifier,
    availableRoles: List<RoleEntity>,
    preSelected: RoleEntity? = null,
    onSelect: (RoleEntity) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedRole: String by remember {
        mutableStateOf(
            preSelected?.role ?: "",
        )
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    ExposedDropdownMenuBox(
        modifier = modifier.focusRequester(focusRequester).widthIn(min = 100.dp),
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(5.dp),
                )
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                            keyboardController?.show()
                        }
                    }
                },
            shape = RoundedCornerShape(5.dp),
            enabled = true,
            value = selectedRole,
            onValueChange = { },
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
        )

        DropdownMenu(
            expanded = isExpanded,
            modifier = Modifier
                .exposedDropdownSize()
                .zIndex(2f),
            properties = PopupProperties(dismissOnClickOutside = false),
            onDismissRequest = { isExpanded = false },
        ) {
            availableRoles.forEach {
                DropdownMenuItem(
                    modifier = Modifier,
                    leadingIcon = null,
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = it.id,
                        )
                    },
                    onClick = {
                        isExpanded = false
                        selectedRole = it.id
                        onSelect(it)
                    },
                )
            }
        }
    }
}

val roles = listOf(
    RoleEntity(
        model = "doming",
        ip = "conclusionemque",
        icon = "lorem",
        bias = "ceteros",
        role = "parturient",
        id = "1",
        temperature = "0.7",
    ),
    RoleEntity(
        model = "repudiandae",
        ip = "mel",
        icon = "a",
        bias = "ipsum",
        role = "inani",
        id = "2",
        temperature = "0.7",
    ),
)

val demoActions = listOf(
    Action(
        role = roles.first(),
    ),
    Action(
        role = roles.get(1),
    ),
    Action(
        role = roles.get(1),
    ),
)

val phases = listOf(
    PhasePresentationModel(
        phaseId = 0,
        actions = demoActions,
    ),
    PhasePresentationModel(
        phaseId = 100,
        actions = demoActions.takeLast(2),
    ),
)

@Preview(showBackground = true)
@Composable
fun JobConfigurationScreenPreview() {
    Box {
        Column {
            TaskUI({})
            ScenarioUI(
                availableRoles = roles,
                phases = phases,
                addPhase = {},
                onUpdatePhase = { _, _, _ -> },
            )
        }
    }
}

@Preview
@Composable
fun JobConfigurationScreenPreviewOld() {
    Box {
        SimplePhase(
            availableRoles = roles,
        )
    }
}
