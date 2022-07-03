package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.Crew

class GetCrew(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int, isMovie: Boolean): List<Crew> {
        val response = if (isMovie)
            repository.getMovieCredits(id = id).crew
        else
            repository.getTvCredits(id = id).crew

        return response
            .filter {
                (it.job == "Screenplay")
                        || (it.job == "Producer")
                        || (it.job == "Director")
                        || (it.job == "Story")
                        || (it.job == "Writer")
                        || (it.job == "Executive Producer")
            }
            .sortedWith(
                compareBy(
                    {
                        it.job == "Producer"
                    },
                    {
                        it.job == "Story"
                    },
                    {
                        it.job == "Executive Producer"
                    },
                    {
                        it.job == "Screenplay"
                    },
                    {
                        it.job == "Writer"
                    },
                    {
                        it.job == "Director"
                    },
                )
            )
    }
}