package com.kavrin.marvin.presentation.screens.home.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kavrin.marvin.domain.use_cases.list.ListUseCases
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.NetworkListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    useCases: ListUseCases,
    networkListener: NetworkListener,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val isConnected = networkListener.checkNetworkAvailability()
    val listTitle = savedStateHandle.get<String>(Constants.LIST_ARGUMENT_KEY_NAME)
    val isMovie = savedStateHandle.get<Boolean>(Constants.LIST_ARGUMENT_KEY_IS_MOVIE)

    val popularMovies = useCases.getPopularMoviesUseCase().cachedIn(viewModelScope)
    val topRatedMovies = useCases.getTopRatedMoviesUseCase().cachedIn(viewModelScope)
    val trendingMovies = useCases.getTrendingMoviesUseCase().cachedIn(viewModelScope)
    val popularTvs = useCases.getPopularTvsUseCase().cachedIn(viewModelScope)
    val topRatedTvs = useCases.getTopRatedTvsUseCase().cachedIn(viewModelScope)
    val trendingTvs = useCases.getTrendingTvsUseCase().cachedIn(viewModelScope)
}