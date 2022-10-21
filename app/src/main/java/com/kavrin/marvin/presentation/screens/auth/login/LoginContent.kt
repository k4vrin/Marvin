package com.kavrin.marvin.presentation.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.kavrin.marvin.presentation.screens.auth.login.components.*
import com.kavrin.marvin.ui.theme.LARGE_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun LoginContent(
    usernameOrEmail: String,
    password: String,
    errorMessage: String,
    isError: Boolean,
    isProcessing: Boolean,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onSocialMediaClicked: (SocialMedia) -> Unit,
    onRecoverClicked: () -> Unit,
    onLoginClicked: (user: String, pass: String) -> Unit

) {

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colors.backGroundColor)
            .padding(horizontal = SMALL_PADDING)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginTopBar(
            onCloseClick = onCloseClick
        )

        LoginHeader()

        Spacer(modifier = Modifier.height(LARGE_PADDING))

        LoginForm(
            usernameOrEmail = usernameOrEmail,
            password = password,
            errorMessage = errorMessage,
            hasError = isError,
            isProcessing = isProcessing,
            focusManager = focusManager,
            onUsernameChange = onUsernameChanged,
            onPasswordChange = onPasswordChanged,
            onLoginClicked = onLoginClicked,
            onRecoverClicked = onRecoverClicked
        )

        ContinueSpacer()

        SocialMediaSignIn(focusManager = focusManager, onIconClicked = onSocialMediaClicked)

        Spacer(modifier = Modifier.height(SMALL_PADDING))

        Register(onRegisterClick = onRegisterClick)

    }
}


