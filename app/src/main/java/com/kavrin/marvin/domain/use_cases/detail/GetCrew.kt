package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.Crew

class GetCrew(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int): List<Crew> {
        return repository.getMovieCredits(id = id).crew
            .filter {
            it.job == "Screenplay" || it.job == "Producer" || it.job == "Director" || it.job == "Story"
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
                        it.job == "Screenplay"
                    },
                    {
                        it.job == "Director"
                    },
                )
            )
    }
}