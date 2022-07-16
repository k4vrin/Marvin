package com.kavrin.marvin.presentation.screens.tv

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.use_cases.tv.TvUseCases
import com.kavrin.marvin.presentation.screens.movie.component.TransitionState
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

    private val _tvRatings: MutableStateFlow<Map<String, String?>?> = MutableStateFlow(null)
    val tvRatings: StateFlow<Map<String, String?>?> = _tvRatings

    private val _tvRuntimeStatusDate: MutableStateFlow<Map<String, String?>?> = MutableStateFlow(null)
    val tvRuntimeStatusDate: StateFlow<Map<String, String?>?> = _tvRuntimeStatusDate

    private val _tvGenres: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val tvGenres: StateFlow<List<String>?> = _tvGenres



    private val _transition = mutableStateOf(TransitionState.Start)
    val transition: State<TransitionState> = _transition

    fun updateTransitionState(enable: Boolean) {
        if (enable) _transition.value = TransitionState.End
    }

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
                _tvGenres.value = useCases.getTvDetails.getGenres()
            }
        }
    }


}