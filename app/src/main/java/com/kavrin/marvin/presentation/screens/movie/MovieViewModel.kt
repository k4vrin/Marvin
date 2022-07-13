package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.movie.DetailUseCases
import com.kavrin.marvin.presentation.screens.movie.component.TransitionState
import com.kavrin.marvin.util.Constants.ARGUMENT_KEY_ID
import com.kavrin.marvin.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCases: DetailUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedMovie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _movieDetails: MutableStateFlow<NetworkResult<SingleMovieApiResponse>> =
        MutableStateFlow(NetworkResult.Loading())
    val movieDetails: StateFlow<NetworkResult<SingleMovieApiResponse>> = _movieDetails

    private val _ratings: MutableStateFlow<NetworkResult<IMDbRatingApiResponse>> =
        MutableStateFlow(NetworkResult.Loading())
    val ratings: StateFlow<NetworkResult<IMDbRatingApiResponse>> = _ratings

    private val _transition = mutableStateOf(TransitionState.Start)
    val transition: State<TransitionState> = _transition

    val id = savedStateHandle.get<Int>(ARGUMENT_KEY_ID)

    fun updateTransitionState(enable: Boolean) {
        if (enable) _transition.value = TransitionState.End
    }

    init {

        viewModelScope.launch(context = Dispatchers.IO) {
            _selectedMovie.value = id?.let { movieId ->
                useCases.getMovie(movieId = movieId)
            }
        }
    }

    fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (id != null) {
                _movieDetails.value = useCases.getMovieDetails(id = id)
                val imdbId = movieDetails.value.data?.imdbId
                if (imdbId != null) {
                    _ratings.value = useCases.getRatings(id = imdbId)
                }
            }
        }
    }

}