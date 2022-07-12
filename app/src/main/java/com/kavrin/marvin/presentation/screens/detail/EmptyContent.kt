package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.LoadingAnimation
import com.kavrin.marvin.ui.theme.*

@Composable
fun EmptyContent(
    isLoading: Boolean,
    isError: Boolean,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,
    onRefresh: (() -> Unit)? = null
) {

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.backGroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    } else if (isError) {
        if (onRefresh != null) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = onRefresh
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(MaterialTheme.colors.backGroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        modifier = Modifier
                            .size(NETWORK_ERROR_ICON_HEIGHT)
                            .alpha(ContentAlpha.disabled),
                        painter = painterResource(id = R.drawable.network_error),
                        contentDescription = "Network Error Icon",
                        tint = if (isSystemInDarkTheme()) LightGray else DarkGray
                    )

                    Text(
                        modifier = Modifier
                            .padding(all = SMALL_PADDING)
                            .alpha(ContentAlpha.disabled),
                        text = errorMessage!!,
                        color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                        textAlign = TextAlign.Center,
                        fontFamily = nunitoTypeFace,
                        fontWeight = FontWeight.Medium,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize
                    )

                }

            }
        }
    }

}