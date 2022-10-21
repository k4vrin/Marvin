package com.kavrin.marvin.presentation.screens.auth.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.topBarContentColor

@Composable
fun LoginTopBar(
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onCloseClick,
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Close Login Screen",
                tint = MaterialTheme.colors.topBarContentColor
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginTopBarPreview() {
    LoginTopBar {}
}