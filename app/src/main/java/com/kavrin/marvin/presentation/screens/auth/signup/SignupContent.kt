package com.kavrin.marvin.presentation.screens.auth.signup

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
import com.kavrin.marvin.presentation.screens.auth.login.components.ContinueSpacer
import com.kavrin.marvin.presentation.screens.auth.login.components.SocialMediaSignIn
import com.kavrin.marvin.presentation.screens.auth.signup.components.SignupForm
import com.kavrin.marvin.presentation.screens.auth.signup.components.SignupHeader
import com.kavrin.marvin.presentation.screens.auth.signup.components.SignupTopBar
import com.kavrin.marvin.ui.theme.LARGE_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun SignupContent(
    username: String,
    email: String,
    password: String,
    usernameErrorMessage: String?,
    hasUsernameError: Boolean = !usernameErrorMessage.isNullOrBlank(),
    emailErrorMessage: String?,
    hasEmailError: Boolean = !emailErrorMessage.isNullOrBlank(),
    passwordErrorMessage: String?,
    hasPasswordError: Boolean = !passwordErrorMessage.isNullOrBlank(),
    isProcessing: Boolean,
    onSignUpClicked: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
        SignupTopBar(onCloseClick = {})

        SignupHeader()

        Spacer(modifier = Modifier.height(LARGE_PADDING))

        SignupForm(
            username,
            email,
            password,
            hasUsernameError,
            usernameErrorMessage,
            hasEmailError,
            emailErrorMessage,
            hasPasswordError,
            passwordErrorMessage,
            isProcessing,
            focusManager,
            onSignUpClicked,
            onUsernameChange,
            onEmailChange,
            onPasswordChange
        )

        ContinueSpacer()

        SocialMediaSignIn(
            focusManager = focusManager,
            onIconClicked = {}
        )
    }
}

