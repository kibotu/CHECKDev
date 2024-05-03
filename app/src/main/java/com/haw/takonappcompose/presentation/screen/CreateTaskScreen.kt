package com.haw.takonappcompose.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
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
import com.haw.takonappcompose.models.Role
import com.haw.takonappcompose.viewmodel.CreateTaskViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTaskScreen(
    navController: NavController,
    viewModel: CreateTaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    SimplePhase(availableRoles = roles)
}

@Composable
fun SimplePhase(
    availableRoles: List<Role>,
    role1: Role? = null,
    role2: Role? = null,
    modifier: Modifier = Modifier,
) {
    var agent1: Role? by remember {
        mutableStateOf(
            role1,
        )
    }
    var agent2: Role? by remember {
        mutableStateOf(
            role2,
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth().heightIn(min = 30.dp, max = 200.dp),
    ) {
        SelectRole(
            availableRoles = availableRoles,
            onSelect = { agent1 = it },
        )
        Arrow(Modifier.rotate(90F).align(Alignment.CenterHorizontally))
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
        modifier = modifier.widthIn(min = 20.dp),
        painter = painterResource(id = R.drawable.arrow_right),
        contentDescription = null,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SelectRole(
    modifier: Modifier = Modifier,
    availableRoles: List<Role>,
    onSelect: (Role) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedRole: String by remember {
        mutableStateOf(
            "",
        )
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    ExposedDropdownMenuBox(
        modifier = modifier.focusRequester(focusRequester),
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
                            text = it.role,
                        )
                    },
                    onClick = {
                        isExpanded = false
                        selectedRole = it.role
                        onSelect(it)
                    },
                )
            }
        }
    }
}

val roles = listOf(
    Role(
        model = "doming",
        ip = "conclusionemque",
        icon = "lorem",
        bias = "ceteros",
        role = "parturient",
    ),
    Role(
        model = "repudiandae",
        ip = "mel",
        icon = "a",
        bias = "ipsum",
        role = "inani",
    ),
)

@Preview
@Composable
fun JobConfigurationScreenPreview() {
    Box() {
        SimplePhase(
            availableRoles = roles,
        )
    }
}
