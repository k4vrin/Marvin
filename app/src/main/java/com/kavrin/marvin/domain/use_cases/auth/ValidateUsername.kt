package com.kavrin.marvin.domain.use_cases.auth

import com.kavrin.marvin.R
import com.kavrin.marvin.util.UiText

class ValidateUsername {

    operator fun invoke(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_blank, arrayOf("username"))
            )
            username.length < 4 -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_length, arrayOf("username", "4"))
            )
            else -> ValidationResult(successful = true)
        }
    }
}