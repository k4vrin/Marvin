package com.kavrin.marvin.presentation.screens.auth

import com.kavrin.marvin.util.UiText

sealed interface ValidationEvent {
    object Success : ValidationEvent
    data class Error(val message: UiText) : ValidationEvent
    object Loading : ValidationEvent
}