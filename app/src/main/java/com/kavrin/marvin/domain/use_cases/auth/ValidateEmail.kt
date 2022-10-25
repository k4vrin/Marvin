package com.kavrin.marvin.domain.use_cases.auth

import com.kavrin.marvin.R
import com.kavrin.marvin.util.PatternChecker
import com.kavrin.marvin.util.UiText
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val patternChecker: PatternChecker
) {

    operator fun invoke(email: String) : ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_blank, arrayOf("email"))
            )
            !patternChecker.matchesEmailPattern(email) -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_valid, arrayOf("email"))
            )
            else -> ValidationResult(successful = true)

        }
    }
}