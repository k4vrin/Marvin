package com.kavrin.marvin.domain.repository

interface AuthDataOp {

    suspend fun userSignUp(email: String, username: String, password: String)
    suspend fun userSignIn(email: String?, username: String?, password: String)
    suspend fun socialMediaSignIn()
    suspend fun socialMediaSignUp()
}
