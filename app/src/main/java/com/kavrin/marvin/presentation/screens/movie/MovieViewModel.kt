package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCases
import com.kavrin.marvin.util.Constants.ARGUMENT_KEY_ID
import com.kavrin.marvin.util.Resource.*
import com.kavrin.marvin.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCases: MovieUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val movieId = savedStateHandle.get<Int>(ARGUMENT_KEY_ID)

    var state by mutableStateOf(MovieDetailsState())
        private set


    fun init() {
        movieId?.let { movieId ->
            getMovieDetails(movieId)
            getMovieCollection()
            getMovieRatings()
        }
    }


    private fun getMovieDetails(id: Int) {
        useCases.getMovieDetails(id = id).onEach { resource ->
            state = when (resource) {
                is Success -> {
                    state.copy(movie = resource.data)
                }
                is Error -> {
                    state.copy(
                        error = resource.message
                            ?: UiText.DynamicString("An unexpected error occurred")
                    )
                }
                is Loading -> {
                    state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getMovieRatings() {
        state.movie?.imdbId?.let { imdbId ->
            useCases.getMovieRatings(id = imdbId).onEach { resource ->
                state = when (resource) {
                    is Success -> {
                        state.copy(ratings = resource.data)
                    }
                    is Error -> {
                        state.copy(
                            ratingsError = resource.message
                                ?: UiText.DynamicString("Ratings not available")
                        )
                    }
                    is Loading -> {
                        state.copy(isRatingsLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getMovieCollection() {
        state.movie?.collectionId?.let { id ->
            useCases.getCollection(id = id).onEach { resource ->
                state = when (resource) {
                    is Success -> {
                        state.copy(
                            collectionOverview = resource.data?.overview,
                            collectionName = resource.data?.name,
                            collectionBackdrop = resource.data?.backdropPath,
                            collectionMovies = resource.data?.parts
                        )
                    }
                    is Error -> {
                        state.copy(
                            collectionError = resource.message
                                ?: UiText.DynamicString("Collection not available")
                        )
                    }
                    is Loading -> {
                        state.copy(isCollectionLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


}