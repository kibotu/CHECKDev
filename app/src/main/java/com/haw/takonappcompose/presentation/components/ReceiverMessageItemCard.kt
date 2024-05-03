package com.haw.takonappcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haw.takonappcompose.R
import com.haw.takonappcompose.ui.theme.GrayColor
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ReceiverMessageItemCard(
    modifier: Modifier = Modifier,
    message: String = "",
    role: String = ""
) {
    Row(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Bottom),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.ic_takon_boot),
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomEnd = 25.dp),
            color = GrayColor
        ) {
            Box(modifier = Modifier.padding(top = 4.dp)) {
                MarkdownText(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 24.dp),
                    markdown = message,
                )

                Text(
                    text = role,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )
            }
        }
    }
}

@Preview
@Composable
fun ReceiverMessageItemPreview() {
    ReceiverMessageItemCard(
        modifier = Modifier.fillMaxWidth(),
        message = "This is my message",
        role = "Programmer"
    )
}

