package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.primaryCardColor

@Composable
fun MarvinBgCard(
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryCardColor,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MEDIUM_PADDING)
        ) {
            content()
        }
    }
}