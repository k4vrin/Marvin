package com.kavrin.marvin.presentation.screens.auth.signup

import com.kavrin.marvin.util.UiText

data class RegisterStates(
    val email: String = "",
    val emailError: UiText? = null,
    val username: String = "",
    val usernameError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isRegisterBtnDisabled: Boolean = false
)
