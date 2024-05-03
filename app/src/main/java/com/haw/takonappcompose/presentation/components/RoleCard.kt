package com.haw.takonappcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haw.takonappcompose.models.Role
import com.haw.takonappcompose.ui.theme.BluePrimary
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

@Composable
fun RoleCard(
    role: Role,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .size(64.dp),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                colorFilter = ColorFilter.tint(BluePrimary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray)
                    .wrapContentSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(min = 128.dp)
                        .wrapContentHeight()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(role.role, fontSize = 22.sp)

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "(${role.model})",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                        )
                    }

                    Text(text = role.bias)
                }
            }
        }
    }
}

@Preview
@Composable
fun RoleCardPreview() {
    TakonAppComposeTheme {
        RoleCard(
            role = Role(
                model = "llama3",
                ip = "bla",
                bias = "world conqueror",
                icon = "bla",
                role = "CEO"
            )
        )
    }
}