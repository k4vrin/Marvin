package com.kavrin.marvin.presentation.screens.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
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

    private val _tvDetailsResponse: MutableStateFlow<NetworkResult<SingleTvApiResponse>> =
        MutableStateFlow(NetworkResult.Loading())
    val tvDetailsResponse: StateFlow<NetworkResult<SingleTvApiResponse>> = _tvDetailsResponse

    private val _tvRatingsResponse: MutableStateFlow<NetworkResult<IMDbRatingApiResponse>> =
        MutableStateFlow(NetworkResult.Loading())
    val tvRatingsResponse: StateFlow<NetworkResult<IMDbRatingApiResponse>> = _tvRatingsResponse

    private val _tvRatings: MutableStateFlow<Map<String, String?>?> = MutableStateFlow(null)
    val tvRatings: StateFlow<Map<String, String?>?> = _tvRatings

    private val _tvRuntimeStatusDate: MutableStateFlow<Map<String, String?>?> = MutableStateFlow(null)
    val tvRuntimeStatusDate: StateFlow<Map<String, String?>?> = _tvRuntimeStatusDate

    private val _tvGenre: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val tvGenre: StateFlow<List<String>?> = _tvGenre


    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            _selectedTv.value = id?.let { tvId ->
                useCases.getTv(tvId = tvId)
            }
        }
    }

    fun getTvDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (id != null) {
                _tvDetailsResponse.value = useCases.getTvDetails(id = id)
                val imdbId = useCases.getTvDetails.getImdbId()
                if (!imdbId.isNullOrBlank()) {
                    _tvRatingsResponse.value = useCases.getTvRatings(id = imdbId)
                    _tvRatings.value = useCases.getTvRatings.getRatingsValue()
                }
                _tvRuntimeStatusDate.value = useCases.getTvDetails.getRuntimeStatusDate()
            }
        }
    }


}