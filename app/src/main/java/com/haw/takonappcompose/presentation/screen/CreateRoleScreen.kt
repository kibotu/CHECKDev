package com.haw.takonappcompose.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.haw.takonappcompose.R
import com.haw.takonappcompose.database.RoleEntity
import com.haw.takonappcompose.navigation.Screen
import com.haw.takonappcompose.ui.theme.BluePrimary
import com.haw.takonappcompose.viewmodel.CreateRoleViewModel
import com.haw.takonappcompose.viewmodel.RoleViewModel
import org.koin.androidx.compose.get

@Composable
fun CreateRoleScreen(
    navController: NavController,
    viewModel: CreateRoleViewModel = viewModel()
) {
    val (nameInput, setNameInput) = remember { mutableStateOf("") }
    val (modelInput, setModelInput) = remember { mutableStateOf("") }
    val (ipInput, setIpInput) = remember { mutableStateOf("") }
    val (iconInput, setIconInput) = remember { mutableStateOf("") }
    val (biasInput, setBiasInput) = remember { mutableStateOf("") }
    val (roleInput, setRoleInput) = remember { mutableStateOf("") }
    val (temperatureInput, setTemperatureInput) = remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

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

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = modelInput,
            onValueChange = { value ->
                setModelInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter model",
                    fontWeight = FontWeight.Bold
                )
            }
        )

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

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = roleInput,
            onValueChange = { value ->
                setRoleInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter role",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        TextField(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            value = temperatureInput,
            onValueChange = { value ->
                setTemperatureInput(value)
            },
            placeholder = {
                Text(
                    text = "Enter temperature",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        OutlinedButton(
            onClick = {
                if (nameInput.isNotBlank()
                    && modelInput.isNotBlank()
                    && ipInput.isNotBlank()
                    && iconInput.isNotBlank()
                    && biasInput.isNotBlank()
                    && roleInput.isNotBlank()
                    && temperatureInput.isNotBlank()
                    )
                {
                    isError = false
                    viewModel.addRole(
                        RoleEntity(
                            id = nameInput,
                            model = modelInput,
                            ip = ipInput,
                            icon = iconInput,
                            bias = biasInput,
                            role = roleInput,
                            temperature = temperatureInput
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