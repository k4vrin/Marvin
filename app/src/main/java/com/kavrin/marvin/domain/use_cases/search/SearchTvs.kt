package com.kavrin.marvin.domain.use_cases.search

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.Tv
import kotlinx.coroutines.flow.Flow

class SearchTvs(
    private val repository: Repository
) {

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    operator fun invoke(query: String): Flow<PagingData<Tv>> {
        require(
            value = query.isNotBlank(),
            lazyMessage = { "The search query can't be empty." }
        )
        return repository.searchTvs(query = query)

    }

}