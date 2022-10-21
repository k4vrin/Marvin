package com.kavrin.marvin.domain.use_cases.auth

data class AuthUseCases(
    val socialMediaSignup: SocialMediaSignup,
    val socialMediaSignIn: SocialMediaSingIn,
    val userSignup: UserSignup,
    val userSignIn: UserSignIn,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateUsername: ValidateUsername
)
