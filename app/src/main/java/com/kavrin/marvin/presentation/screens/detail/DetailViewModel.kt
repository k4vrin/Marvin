package com.kavrin.marvin.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.movie.api.Cast
import com.kavrin.marvin.domain.model.movie.api.Crew
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.use_cases.detail.DetailUseCases
import com.kavrin.marvin.util.Constants.DETAILS_ARGUMENT_KEY_BOOL
import com.kavrin.marvin.util.Constants.DETAILS_ARGUMENT_KEY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: DetailUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedMovie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _selectedTv: MutableStateFlow<Tv?> = MutableStateFlow(null)
    val selectedTv: StateFlow<Tv?> = _selectedTv

    private val _genres: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val genres: StateFlow<List<String>?> = _genres

    private val _cast: MutableStateFlow<List<Cast>?> = MutableStateFlow(null)
    val cast: StateFlow<List<Cast>?> = _cast

    private val _crew: MutableStateFlow<List<Crew>?> = MutableStateFlow(null)
    val crew: StateFlow<List<Crew>?> = _crew

    val id = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY_ID)
    val isMovie = savedStateHandle.get<Boolean>(DETAILS_ARGUMENT_KEY_BOOL)

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            isMovie?.let {
                if (it) {
                    _selectedMovie.value = id?.let { movieId ->
                        useCases.getMovie(movieId = movieId)
                    }
                } else {
                    _selectedTv.value = id?.let { tvId ->
                        useCases.getTv(tvId = tvId)
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (id != null && isMovie != null) {
                _cast.value = useCases.getCast(id = id, isMovie = isMovie)
                _crew.value = useCases.getCrew(id = id, isMovie = isMovie)
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch(context = Dispatchers.IO) {
            isMovie?.let {
                if (it) {
                    _genres.value =
                        selectedMovie.value?.let { it1 -> useCases.getMovieGenres(it1.genreIds) }
                } else {
                    _genres.value =
                        selectedTv.value?.let { it1 -> useCases.getTvGenres(it1.genreIds) }
                }
            }
        }
    }


}