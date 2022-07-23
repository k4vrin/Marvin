package com.kavrin.marvin.presentation.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.ConnectivityStatus
import com.kavrin.marvin.ui.theme.HOME_TOP_BAR_ELEVATION
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor

@Composable
fun ListToolbar(
    title: String?,
    isConnected: Boolean,
    onBackClicked: () -> Unit,
) {


    Column {
        ConnectivityStatus(isConnected = isConnected)

        TopAppBar(
            //// Title ////
            title = {
                Text(
                    text = title ?: "List",
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.topBarContentColor
                )
            },

            //// Background Color ////
            backgroundColor = MaterialTheme.colors.topBarBgColor,
            navigationIcon = {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon),
                        tint = MaterialTheme.colors.topBarContentColor
                    )
                }
            },
            elevation = HOME_TOP_BAR_ELEVATION
        )
    }
}