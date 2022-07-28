package com.kavrin.marvin.data.paging_source.series

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.tv.entities.Tv

class SearchTvSource(
    private val tvService: TMDBTvService,
    private val query: String
) : PagingSource<Int, Tv>() {

    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return (state.anchorPosition ?: 0) - (state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val page = params.key ?: 1
            val apiResponse = tvService.searchTvs(query = query, page = page)
            val tvs = apiResponse.tvs
            if (tvs.isNotEmpty()) {
                LoadResult.Page(
                    data = tvs,
                    prevKey = page - 1,
                    nextKey = page + 1
                )
            } else {
                LoadResult.Page(
                    data = tvs,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}