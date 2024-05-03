package com.haw.takonappcompose.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.viewmodel.CreateRoleViewModel

@Composable
fun CreateRoleScreen(
    navController: NavController,
    viewModel: CreateRoleViewModel = viewModel()
) {
    val (nameInput, setNameInput) = remember { mutableStateOf("") }
    val (ipInput, setIpInput) = remember { mutableStateOf("") }
    val (iconInput, setIconInput) = remember { mutableStateOf("") }
    val (biasInput, setBiasInput) = remember { mutableStateOf("") }
    var temperatureSliderPosition by remember { mutableFloatStateOf(0f) }
    var isError by remember { mutableStateOf(false) }
    var selectedModelIndex by remember { mutableIntStateOf(0) }
    var selectedRoleIndex by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = nameInput,
            onValueChange = { value ->
                setNameInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter name",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        Text(text = "Model:")
        Dropdown(
            viewModel = viewModel,
            items = viewModel.knownModels.map { it.name },
            selectedIndex = selectedModelIndex
        ) {
            selectedModelIndex = it
        }

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = ipInput,
            onValueChange = { value ->
                setIpInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter ip",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = iconInput,
            onValueChange = { value ->
                setIconInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter icon",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = biasInput,
            onValueChange = { value ->
                setBiasInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter bias",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        Text(text = "Role:")
        Dropdown(
            viewModel = viewModel,
            items = viewModel.knownRoles.map { it.name },
            selectedIndex = selectedRoleIndex
        ) {
            selectedRoleIndex = it
        }

        Column {
            Text(text = "Temperature:")
            Slider(
                value = temperatureSliderPosition,
                onValueChange = { temperatureSliderPosition = it },
            )
            Text(text = temperatureSliderPosition.toString())
        }



        OutlinedButton(
            onClick = {
                if (nameInput.isNotBlank()
                    && ipInput.isNotBlank()
                    && iconInput.isNotBlank()
                    && biasInput.isNotBlank()
                ) {
                    isError = false
                    viewModel.addRole(
                        RoleEntity(
                            id = nameInput,
                            model = viewModel.knownModels[selectedModelIndex].name,
                            ip = ipInput,
                            icon = iconInput,
                            bias = biasInput,
                            role = viewModel.knownRoles[selectedRoleIndex].name,
                            temperature = temperatureSliderPosition.toString()
                        )
                    )
                    navController.popBackStack()
                } else {
                    isError = true
                }
            }
        ) {
            Text("Create role")
        }

        if (isError) {
            Text(
                text = "Please fill out all fields in order to submit",
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRoleScreenPreview() {
    CreateRoleScreen(
        navController = rememberNavController()
    )
}

@Composable
fun Dropdown(viewModel: CreateRoleViewModel, items: List<String>, selectedIndex: Int, selectedIndexCallback: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)) {
        Text(
            items[selectedIndex],
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = { Text(text = s) },
                    onClick = {
                        selectedIndexCallback.invoke(index)
                        expanded = false
                    }
                )
            }
        }
    }
}