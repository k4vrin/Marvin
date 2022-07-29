package com.kavrin.marvin.domain.repository

import com.kavrin.marvin.domain.model.person.PersonApiResponse
import retrofit2.Response

interface PersonRemoteDataSource {

    suspend fun getPerson(id: Int): Response<PersonApiResponse>
}