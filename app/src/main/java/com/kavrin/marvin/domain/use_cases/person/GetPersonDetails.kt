package com.kavrin.marvin.domain.use_cases.person

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.person.*
import com.kavrin.marvin.util.NetworkResult
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class GetPersonDetails(
    private val repository: Repository
) {

    private var data: PersonApiResponse? = null

    suspend operator fun invoke(id: Int): NetworkResult {
        val response =
            try {
                repository.getPerson(id = id)
            } catch (e: Exception) {
                return NetworkResult.Error(message = e.message)
            }
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.code() == 401 -> NetworkResult.Error(message = "Invalid API key.")
            response.code() == 404 -> NetworkResult.Error(message = "The resources could not be found.")
            response.isSuccessful -> {
                data = response.body()
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getToolbarDetails(): Map<String, String?> {
        return mapOf(
            PersonConstants.NAME to data?.name,
            PersonConstants.BIRTH_DATE to data?.birthday,
            PersonConstants.POSTER to data?.profilePath
        )
    }

    fun getInfoDetails(): Map<String, Int?> {
        return buildMap {
            val age = data?.birthday?.let { calcAge(it) }
            val totalMovies = (data?.movieCredits?.personMovieCast?.size ?: 0) + (data?.movieCredits?.personMovieCrew?.size ?: 0)
            val totalTvs = (data?.tvCredits?.personTvCast?.size ?: 0) + (data?.tvCredits?.personTvCrew?.size ?: 0)
            val bio = data?.biography

            put(key = PersonConstants.AGE, value = age)
            put(key = PersonConstants.TOTAL_MOVIES, value = totalMovies)
            put(key = PersonConstants.TOTAL_TVS, value = totalTvs)
        }
    }

    fun getBio(): String? {
        return data?.biography
    }

    fun getCastMovies(): List<PersonMovieCast>? {
        return data?.movieCredits?.personMovieCast?.sortedByDescending { it.popularity }
    }

    fun getCrewMovies(): List<PersonMovieCrew>? {
        return data?.movieCredits?.personMovieCrew?.sortedByDescending { it.popularity }
    }

    fun getCastTvs(): List<PersonTvCast>? {
        return data?.tvCredits?.personTvCast?.sortedByDescending { it.popularity }
    }

    fun getCrewTvs(): List<PersonTvCrew>? {
        return data?.tvCredits?.personTvCrew?.sortedByDescending { it.popularity }
    }

    private fun calcAge(age: String): Int {
        val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val a = LocalDate.parse(age, parsePattern)
        val b = LocalDate.now()
        return Period.between(a, b).years
    }

}

object PersonConstants {
    const val NAME = "name"
    const val BIRTH_DATE = "birthdate"
    const val POSTER = "poster"
    const val AGE = "age"
    const val TOTAL_MOVIES = "total_movies"
    const val TOTAL_TVS = "total_tvs"
}