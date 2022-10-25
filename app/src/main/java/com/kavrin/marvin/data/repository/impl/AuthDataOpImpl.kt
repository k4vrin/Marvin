package com.kavrin.marvin.data.repository.impl

import com.kavrin.marvin.domain.repository.AuthDataOp
import com.parse.ParseUser
import com.parse.coroutines.suspendSignUp

class AuthDataOpImpl : AuthDataOp {
    override suspend fun userSignUp(email: String, username: String, password: String) {
        val user = ParseUser().apply {
            setUsername(username)
            setEmail(email)
            setPassword(password)
        }

        user.suspendSignUp()
    }

    override suspend fun userSignIn(email: String?, username: String?, password: String) {
        /* TODO:  */
    }

    override suspend fun socialMediaSignIn() {
        /* TODO:  */
    }

    override suspend fun socialMediaSignUp() {
        /* TODO:  */
    }
}