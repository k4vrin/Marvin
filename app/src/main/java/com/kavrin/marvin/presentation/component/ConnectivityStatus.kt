package com.kavrin.marvin.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.GreenRYB
import com.kavrin.marvin.ui.theme.RoseMadder
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import kotlinx.coroutines.delay


@Composable
fun ConnectivityStatus(isConnected: Boolean) {
    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnected = isConnected)
    }

    LaunchedEffect(key1 = isConnected) {
        if (!isConnected) {
            visibility = true
        } else {
            delay(1000)
            visibility = false
        }
    }

}


@Composable
fun ConnectivityStatusBox(isConnected: Boolean) {

    val backgroundColor by animateColorAsState(targetValue = if (isConnected) GreenRYB else RoseMadder)
    val message = if (isConnected) "Back Online!" else "No Internet Connection"
    val icon =
        if (isConnected) R.drawable.ic_connectivity_available else R.drawable.ic_connectivity_unavailable

    Box(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .padding(SMALL_PADDING),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Connectivity Icon",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(SMALL_PADDING))

            Text(
                text = message,
                color = Color.White,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }

    }
}