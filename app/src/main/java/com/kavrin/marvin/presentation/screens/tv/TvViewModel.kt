package com.kavrin.marvin.presentation.screens.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.use_cases.tv.TvUseCases
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val useCases: TvUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>(Constants.ARGUMENT_KEY_ID)

    private val _selectedTv: MutableStateFlow<Tv?> = MutableStateFlow(null)
    val selectedTv: StateFlow<Tv?> = _selectedTv

    private val _tvDetailsResponse: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Loading())
    val tvDetailsResponse: StateFlow<NetworkResult> = _tvDetailsResponse

    private val _tvRatingsResponse: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Loading())
    val tvRatingsResponse: StateFlow<NetworkResult> = _tvRatingsResponse

    private val _tvRatings: MutableStateFlow<Map<String, String?>> = MutableStateFlow(emptyMap())
    val tvRatings: StateFlow<Map<String, String?>> = _tvRatings

    private val _tvRuntimeStatusDate: MutableStateFlow<Map<String, String?>> = MutableStateFlow(emptyMap())
    val tvRuntimeStatusDate: StateFlow<Map<String, String?>> = _tvRuntimeStatusDate

    private val _tvGenres: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val tvGenres: StateFlow<List<String>?> = _tvGenres

    private val _tvCast = MutableStateFlow<List<Cast>?>(null)
    val tvCast: StateFlow<List<Cast>?> = _tvCast

    private val _tvCrew = MutableStateFlow<List<Crew>?>(null)
    val tvCrew: StateFlow<List<Crew>?> = _tvCrew

    private val _tvReviews = MutableStateFlow<List<Review>?>(null)
    val tvReviews: StateFlow<List<Review>?> = _tvReviews

    private val _tvTrailer = MutableStateFlow<Video?>(null)
    val tvTrailer: StateFlow<Video?> = _tvTrailer

    private val _tvTrailerBackdrop = MutableStateFlow<Backdrop?>(null)
    val tvTrailerBackdrop: StateFlow<Backdrop?> = _tvTrailerBackdrop

    private val _tvVideos = MutableStateFlow<List<Video>?>(null)
    val tvVideos: StateFlow<List<Video>?> = _tvVideos

    private val _tvSeasons = MutableStateFlow<List<Season>?>(null)
    val tvSeasons: StateFlow<List<Season>?> = _tvSeasons

    private val _tvEpisodesToAir = MutableStateFlow<Map<String, EpisodeToAir?>>(emptyMap())
    val tvEpisodesToAir: StateFlow<Map<String, EpisodeToAir?>> = _tvEpisodesToAir

    private val _tvSimilar = MutableStateFlow<List<Tv>?>(null)
    val tvSimilar: StateFlow<List<Tv>?> = _tvSimilar

    private val _tvRecommended = MutableStateFlow<List<Tv>?>(null)
    val tvRecommended: StateFlow<List<Tv>?> = _tvRecommended

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            _selectedTv.value = id?.let { tvId ->
                useCases.getTv(tvId = tvId)
            }
        }
    }

    fun getTvDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _tvDetailsResponse.value = NetworkResult.Loading()
            if (id != null) {
                _tvDetailsResponse.value = useCases.getTvDetails(id = id)
                val imdbId = useCases.getTvDetails.getImdbId()
                if (!imdbId.isNullOrBlank()) {
                    _tvRatingsResponse.value = useCases.getTvRatings(id = imdbId)
                    _tvRatings.value = useCases.getTvRatings.getRatingsValue()
                } else {
                    _tvRatingsResponse.value = NetworkResult.Success()
                }
                _tvRuntimeStatusDate.value = useCases.getTvDetails.getRuntimeStatusDateTotal()
                _tvGenres.value = useCases.getTvDetails.getGenres()
                _tvCast.value = useCases.getTvDetails.getCast()
                _tvCrew.value = useCases.getTvDetails.getCrew()
                _tvReviews.value = useCases.getTvDetails.getReviews()
                _tvTrailer.value = useCases.getTvDetails.getOfficialTrailer()
                _tvTrailerBackdrop.value = useCases.getTvDetails.getTrailerBackdrop()
                _tvVideos.value = useCases.getTvDetails.getVideos()
                _tvSeasons.value = useCases.getTvDetails.getSeasons()
                _tvEpisodesToAir.value = useCases.getTvDetails.getEpisodesToAir()
                _tvSimilar.value = useCases.getTvDetails.getSimilarTvs()
                _tvRecommended.value = useCases.getTvDetails.getRecommendedTvs()
            }
        }
    }


}