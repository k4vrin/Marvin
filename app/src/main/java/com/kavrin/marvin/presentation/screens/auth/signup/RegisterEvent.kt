package com.kavrin.marvin.presentation.screens.auth.signup

sealed interface RegisterEvent {
    data class UsernameChanged(val username: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class EmailChanged(val email: String) : RegisterEvent
    object Submit : RegisterEvent
}