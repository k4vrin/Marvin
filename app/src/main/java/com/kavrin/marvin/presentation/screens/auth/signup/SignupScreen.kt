package com.kavrin.marvin.presentation.screens.auth.signup

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    ///// Status Bar /////
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
        username =,
        email =,
        password =,
        hasUsernameError =,
        usernameErrorMessage =,
        hasEmailError =,
        emailErrorMessage =,
        hasPasswordError =,
        passwordErrorMessage =,
        isProcessing =,
        onSignUpClicked =,
        onUsernameChange =,
        onEmailChange =,
        onPasswordChange =
    )

}