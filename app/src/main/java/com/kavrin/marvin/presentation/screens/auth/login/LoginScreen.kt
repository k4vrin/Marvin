package com.kavrin.marvin.presentation.screens.auth.login

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
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


    LoginContent(
        usernameOrEmail = "",
        password = "",
        errorMessage = "",
        isError = false,
        isProcessing = false,
        onCloseClick = { /*TODO*/ },
        onUsernameChanged = {},
        onPasswordChanged = {},
        onLoginClicked = { user, pass ->  },
        onRecoverClicked = {},
        onRegisterClick = {},
        onSocialMediaClicked = {}
    )
}