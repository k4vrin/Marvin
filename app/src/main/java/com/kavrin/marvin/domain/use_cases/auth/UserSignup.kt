package com.kavrin.marvin.domain.use_cases.auth

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.util.Resource
import com.kavrin.marvin.util.UiText
import com.parse.ParseException
import kotlinx.coroutines.flow.flow

class UserSignup(
    private val repository: Repository
) {

    suspend operator fun invoke(email: String, username: String, password: String)= flow {
        try {
            repository.signUp(email, username, password)
            emit(Resource.Success(data = Unit))
        } catch (e: ParseException) {
            emit(Resource.Error(message = UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }
}