package com.kavrin.marvin.presentation.screens.auth.login

data class LoginStates(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoginBrnDisabled: Boolean = false
)
