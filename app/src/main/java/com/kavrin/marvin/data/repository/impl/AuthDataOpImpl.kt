package com.kavrin.marvin.data.repository.impl

import com.kavrin.marvin.domain.repository.AuthDataOp
import com.parse.ParseUser
import com.parse.coroutines.suspendSignUp

class AuthDataOpImpl : AuthDataOp {
    override suspend fun userSignUp(email: String, username: String, password: String) {
        val user = ParseUser().apply {
            setEmail(email)
            setUsername(username)
            setPassword(password)
        }

        val result = user.suspendSignUp()
    }

    override suspend fun userSignIn(email: String?, username: String?, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun socialMediaSignIn() {
        TODO("Not yet implemented")
    }

    override suspend fun socialMediaSignUp() {
        TODO("Not yet implemented")
    }
}