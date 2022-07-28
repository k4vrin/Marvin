package com.kavrin.marvin.presentation.screens.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kavrin.marvin.ui.theme.CyberYellow
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.nunitoTypeFace


@Composable
fun ErrorStatus(
    message: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ErrorStatusBox(
            message = message,
            modifier = modifier
        )
    }
}



@Composable
fun ErrorStatusBox(
    message: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(color = CyberYellow)
            .fillMaxWidth()
            .padding(SMALL_PADDING),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Warning,
                contentDescription = "Warning Icon",
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(SMALL_PADDING))

            Text(
                text = message,
                color = Color.Black,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }

    }

}