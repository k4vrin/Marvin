package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCases
import com.kavrin.marvin.presentation.component.FabState
import com.kavrin.marvin.presentation.component.RatingState
import com.kavrin.marvin.util.Constants.ARGUMENT_KEY_ID
import com.kavrin.marvin.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarState
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCases: MovieUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>(ARGUMENT_KEY_ID)

    private val _ratingAnimationState = mutableStateOf(
        MutableTransitionState(RatingState.Start)
    )
    val ratingAnimationState: State<MutableTransitionState<RatingState>> = _ratingAnimationState

    private val _fabState = mutableStateOf(MutableTransitionState(FabState.Start))
    val fabState: State<MutableTransitionState<FabState>> = _fabState

    private val _collapsingToolbar = mutableStateOf(
        CollapsingToolbarScaffoldState(
            CollapsingToolbarState()
        )
    )
    val collapsingToolbar: State<CollapsingToolbarScaffoldState> = _collapsingToolbar

    private val _movieDetailsResponse =
        MutableStateFlow<NetworkResult>(NetworkResult.Loading())
    val movieDetailsResponse: StateFlow<NetworkResult> = _movieDetailsResponse

    private val _toolbarInfo = MutableStateFlow<Map<String, String?>>(emptyMap())
    val toolbarInfo: StateFlow<Map<String, String?>> = _toolbarInfo

    private val _releaseRuntimeStatus = MutableStateFlow<Map<String, String?>>(emptyMap())
    val releaseRuntimeStatus: StateFlow<Map<String, String?>> = _releaseRuntimeStatus

    private val _movieOverview = MutableStateFlow<String?>(null)
    val movieOverview: StateFlow<String?> = _movieOverview

    private val _movieGenre = MutableStateFlow<List<String>?>(null)
    val movieGenre: StateFlow<List<String>?> = _movieGenre

    private val _movieCast = MutableStateFlow<List<Cast>?>(null)
    val movieCast: StateFlow<List<Cast>?> = _movieCast

    private val _movieCrew = MutableStateFlow<List<Crew>?>(null)
    val movieCrew: StateFlow<List<Crew>?> = _movieCrew

    private val _movieTrailer = MutableStateFlow<Video?>(null)
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

    private val _movieCollectionInfo: MutableStateFlow<Map<String, String?>?> =
        MutableStateFlow(null)
    val movieCollectionInfo: StateFlow<Map<String, String?>?> = _movieCollectionInfo

    private val _movieCollection: MutableStateFlow<List<Movie>?> = MutableStateFlow(null)
    val movieCollection: StateFlow<List<Movie>?> = _movieCollection

    private val _movieRatingResponse: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Success())
    val movieRatingResponse: StateFlow<NetworkResult> = _movieRatingResponse

    private val _movieRatings: MutableStateFlow<Map<String, String?>> = MutableStateFlow(emptyMap())
    val movieRatings: StateFlow<Map<String, String?>> = _movieRatings

    fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetailsResponse.value = NetworkResult.Loading()
            id?.let { id ->

                _movieDetailsResponse.value = useCases.getMovieDetails(id = id)
                _toolbarInfo.value = useCases.getMovieDetails.getMovieToolbar()
                _releaseRuntimeStatus.value = useCases.getMovieDetails.getReleaseRuntimeStatus()
                _movieOverview.value = useCases.getMovieDetails.getOverview()
                _movieGenre.value = useCases.getMovieDetails.getGenres()
                _movieCast.value = useCases.getMovieDetails.getCast()
                _movieCrew.value = useCases.getMovieDetails.getCrew()
                _movieTrailer.value = useCases.getMovieDetails.getOfficialTrailer()
                _movieVideos.value = useCases.getMovieDetails.getVideos()
                _trailerBackdrop.value = useCases.getMovieDetails.getTrailerBackdrop()
                _movieReviews.value = useCases.getMovieDetails.getReviews()
                _movieRecommend.value = useCases.getMovieDetails.getRecommendations()
                _movieSimilar.value = useCases.getMovieDetails.getSimilar()

                val collectionId = useCases.getMovieDetails.getCollectionId()
                if (collectionId != null) {
                    _movieCollectionRes.value = useCases.getCollection(id = collectionId)
                    _movieCollectionInfo.value =
                        useCases.getCollection.getCollectionNameAndOverview()
                    _movieCollection.value = useCases.getCollection.getCollectionMovies()
                }

//                val imdbId = useCases.getMovieDetails.getImdbId()
//                if (!imdbId.isNullOrBlank()) {
//                    _movieRatingResponse.value = useCases.getMovieRatings(id = imdbId)
//                    _movieRatings.value = useCases.getMovieRatings.getRatingsValue()
//                } else {
//                    _movieRatingResponse.value = NetworkResult.Success()
//                }

            }
        }
    }

}