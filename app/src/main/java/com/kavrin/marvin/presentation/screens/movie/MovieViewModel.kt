package com.kavrin.marvin.presentation.screens.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCases
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
    private val useCases: MovieUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>(ARGUMENT_KEY_ID)

    private val _selectedMovie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _movieDetailsResponse: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Loading())
    val movieDetailsResponse: StateFlow<NetworkResult> = _movieDetailsResponse

    private val _movieRuntime: MutableStateFlow<Int?> = MutableStateFlow(null)
    val movieRuntime: StateFlow<Int?> = _movieRuntime

    private val _movieGenre: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val movieGenre: StateFlow<List<String>?> = _movieGenre

    private val _movieCast: MutableStateFlow<List<Cast>?> = MutableStateFlow(null)
    val movieCast: StateFlow<List<Cast>?> = _movieCast

    private val _movieCrew: MutableStateFlow<List<Crew>?> = MutableStateFlow(null)
    val movieCrew: StateFlow<List<Crew>?> = _movieCrew

    private val _movieTrailer: MutableStateFlow<Video?> = MutableStateFlow(null)
    val movieTrailer: StateFlow<Video?> = _movieTrailer

    private val _movieVideos: MutableStateFlow<List<Video>?> = MutableStateFlow(null)
    val movieVideos: StateFlow<List<Video>?> = _movieVideos

    private val _trailerBackdrop: MutableStateFlow<Backdrop?> = MutableStateFlow(null)
    val trailerBackdrop: StateFlow<Backdrop?> = _trailerBackdrop

    private val _movieReviews: MutableStateFlow<List<Review>?> = MutableStateFlow(null)
    val movieReviews: StateFlow<List<Review>?> = _movieReviews

    private val _movieRecommend: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val movieRecommend: StateFlow<List<Movie>?> = _movieRecommend

    private val _movieSimilar: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val movieSimilar: StateFlow<List<Movie>?> = _movieSimilar

    private val _movieCollectionRes: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Loading())
    val movieCollectionRes: StateFlow<NetworkResult> = _movieCollectionRes

    private val _movieCollectionName: MutableStateFlow<Map<String, String?>?> =
        MutableStateFlow(null)
    val movieCollectionName: StateFlow<Map<String, String?>?> = _movieCollectionName

    private val _movieCollection: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val movieCollection: StateFlow<List<Movie>?> = _movieCollection

    private val _movieRatingResponse: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Loading())
    val movieRatingResponse: StateFlow<NetworkResult> = _movieRatingResponse

    private val _movieRatings: MutableStateFlow<Map<String, String?>> = MutableStateFlow(emptyMap())
    val movieRatings: StateFlow<Map<String, String?>> = _movieRatings

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            _selectedMovie.value = id?.let { movieId ->
                useCases.getMovie(movieId = movieId)
            }
        }
    }

    fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetailsResponse.value = NetworkResult.Loading()
            if (id != null) {
                _movieDetailsResponse.value = useCases.getMovieDetails(id = id)
                _movieRuntime.value = useCases.getMovieDetails.getRuntime()
                _movieGenre.value = useCases.getMovieDetails.getGenre()
                val imdbId = useCases.getMovieDetails.getImdbId()
                if (!imdbId.isNullOrBlank()) {
                    _movieRatingResponse.value = useCases.getMovieRatings(id = imdbId)
                    _movieRatings.value = useCases.getMovieRatings.getRatingsValue()
                } else {
                    _movieRatingResponse.value = NetworkResult.Success()
                }
                _movieCast.value = useCases.getMovieDetails.getCast()
                _movieCrew.value = useCases.getMovieDetails.getCrew()
                _movieTrailer.value = useCases.getMovieDetails.getOfficialTrailer()
                _movieVideos.value = useCases.getMovieDetails.getVideos()
                _trailerBackdrop.value = useCases.getMovieDetails.getTrailerBackdrop()
                _movieReviews.value = useCases.getMovieDetails.getReviews()
                val collectionId = useCases.getMovieDetails.getCollectionId()
                if (collectionId != null) {
                    _movieCollectionRes.value = useCases.getCollection(id = collectionId)
                    _movieCollectionName.value = useCases.getCollection.getCollectionNameAndOverview()
                    _movieCollection.value = useCases.getCollection.getCollectionMovies()
                }
                _movieRecommend.value = useCases.getMovieDetails.getRecommendations()
                _movieSimilar.value = useCases.getMovieDetails.getSimilar()
            }
        }
    }

}