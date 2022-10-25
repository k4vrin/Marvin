package com.kavrin.marvin.domain.use_cases.auth

import com.kavrin.marvin.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)