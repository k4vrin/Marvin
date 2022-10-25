package com.kavrin.marvin.presentation.screens.auth.signup

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    /* ///// Status Bar ///// */
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val statusBarColor = MaterialTheme.colors.backGroundColor
    LaunchedEffect(key1 = true) {
        uiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }

    SignupContent(
        username = state.username,
        email = state.email,
        password = state.password,
        usernameErrorMessage = state.usernameError?.asString(),
        emailErrorMessage = state.emailError?.asString(),
        passwordErrorMessage = state.passwordError?.asString(),
        isProcessing = state.isRegisterBtnDisabled,
        onSignUpClicked = {
            viewModel.onEvent(event = RegisterEvent.Submit)
        },
        onUsernameChange = {
            viewModel.onEvent(RegisterEvent.UsernameChanged(it))
        },
        onEmailChange = {
            viewModel.onEvent(RegisterEvent.EmailChanged(it))
        },
        onPasswordChange = {
            viewModel.onEvent(RegisterEvent.PasswordChanged(it))
        }
    )

}