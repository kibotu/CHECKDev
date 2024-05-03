package com.haw.takonappcompose.presentation.screen

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.haw.takonappcompose.models.fromUser
import com.haw.takonappcompose.presentation.components.MessengerItemCard
import com.haw.takonappcompose.presentation.components.ReceiverMessageItemCard
import com.haw.takonappcompose.presentation.components.ToolbarMessageTakon
import com.haw.takonappcompose.presentation.components.WriteMessageCard
import com.haw.takonappcompose.viewmodel.TakonViewModel

@Composable
fun MessageScreen(
    navController: NavController,
    viewModel: TakonViewModel = viewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = messages.size) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(messages.size - 1, 1)
        }
    }

    val (input, setInput) = remember { mutableStateOf("") }

    Column {

        ToolbarMessageTakon(
            clear = { viewModel.clear() },
            loading = loading
        )

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, bottom = 64.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    horizontalAlignment = Alignment.End,
                    state = scrollState
                ) {
                    items(messages) { message ->
                        if (message.fromUser) {
                            MessengerItemCard(
                                modifier = Modifier.align(Alignment.End).padding(bottom = 24.dp),
                                message = message.content
                            )
                        } else {
                            ReceiverMessageItemCard(
                                message = message.content,
                                role = message.role,
                            )
                        }
                    }
                }
            }

            WriteMessageCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                value = input,
                onValueChange = { value ->
                    setInput(value)
                },
                onClickSend = {
                    if (input.isNotEmpty()) {
                        viewModel.askQuestion(question = input)
                        setInput("")
                    }
                },
            )
        }
    }

}

@Preview
@Composable
fun MessageScreenPreview() {
    MessageScreen(
        navController = rememberNavController()
    )
}