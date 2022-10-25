package com.kavrin.marvin.domain.use_cases.auth

import com.kavrin.marvin.R
import com.kavrin.marvin.util.UiText

class ValidatePassword {

    operator fun invoke(password: String): ValidationResult {
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() && it.isUpperCase() } && password.any { it.isLetter() && it.isLowerCase() }
        return when {
            password.length < 8 -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    R.string.error_length,
                    arrayOf("password", "8")
                )
            )
            !containsLettersAndDigits -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_contain)
            )
            else -> ValidationResult(successful = true)
        }
    }
}