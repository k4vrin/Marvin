package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.primaryCardColor

@Composable
fun MarvinBgCard(
    verticalPadding: Dp = MEDIUM_PADDING,
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryCardColor,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding)
        ) {
            content()
        }
    }
}

@Composable
fun MarvinDivider() {
    Divider(
        modifier = Modifier
            .padding(vertical = MEDIUM_PADDING),
        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
    )
}

