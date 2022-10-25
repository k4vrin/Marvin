package com.kavrin.marvin.util

import android.util.Patterns

interface PatternChecker {
    fun matchesEmailPattern(email: String): Boolean
}

class PatternCheckerImpl : PatternChecker {
    override fun matchesEmailPattern(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}