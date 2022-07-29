package com.kavrin.marvin.data.repository.impl

import com.kavrin.marvin.data.remote.TMDBPersonService
import com.kavrin.marvin.domain.model.person.PersonApiResponse
import com.kavrin.marvin.domain.repository.PersonRemoteDataSource
import retrofit2.Response

class PersonRemoteDataSourceImpl(
    private val personService: TMDBPersonService
) : PersonRemoteDataSource {

    override suspend fun getPerson(id: Int): Response<PersonApiResponse> {
        return personService.getPersonDetails(id = id)
    }
}