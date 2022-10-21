package com.kavrin.marvin.presentation.screens.tv.tv_season

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.common.Cast
import com.kavrin.marvin.domain.model.common.Crew
import com.kavrin.marvin.domain.use_cases.tv_season.EpisodeSummary
import com.kavrin.marvin.domain.use_cases.tv_season.TvSeasonUseCases
import com.kavrin.marvin.util.Constants
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
class SeasonViewModel @Inject constructor(
    private val useCases: TvSeasonUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>(Constants.ARGUMENT_KEY_ID)
    val seasonNumber = savedStateHandle.get<Int>(Constants.ARGUMENT_KEY_SEASON_NUMBER)

    private val _seasonCollapsingToolbar = mutableStateOf(
        CollapsingToolbarScaffoldState(
            CollapsingToolbarState()
        )
    )
    val seasonCollapsingToolbar: State<CollapsingToolbarScaffoldState> = _seasonCollapsingToolbar

    private val _episodeCollapsingToolbar = mutableStateOf(
        CollapsingToolbarScaffoldState(
            CollapsingToolbarState()
        )
    )
    val episodeCollapsingToolbar: State<CollapsingToolbarScaffoldState> = _episodeCollapsingToolbar


    private val _networkResult = MutableStateFlow<NetworkResult>(NetworkResult.Loading())
    val networkResult: StateFlow<NetworkResult> = _networkResult

    private val _seasonInfo = MutableStateFlow<Map<String, String?>>(emptyMap())
    val seasonInfo: StateFlow<Map<String, String?>> = _seasonInfo

    private val _seasonEpisodes = MutableStateFlow<List<EpisodeSummary>>(emptyList())
    val seasonEpisodes: StateFlow<List<EpisodeSummary>> = _seasonEpisodes

    private val _episodeToolbar = MutableStateFlow<Map<String, String?>>(emptyMap())
    val episodeToolbar: StateFlow<Map<String, String?>> = _episodeToolbar

    private val _episodeInfo = MutableStateFlow<Map<String, String?>>(emptyMap())
    val episodeInfo: StateFlow<Map<String, String?>> = _episodeInfo

    private val _episodeCast = MutableStateFlow<List<Cast>>(emptyList())
    val episodeCast: StateFlow<List<Cast>> = _episodeCast

    private val _episodeCrew = MutableStateFlow<List<Crew>>(emptyList())
    val episodeCrew: StateFlow<List<Crew>> = _episodeCrew

    fun getSeason() {
        viewModelScope.launch(Dispatchers.IO) {
            if (id != null && seasonNumber != null) {
                _networkResult.value = useCases.getTvSeason(id = id, seasonNumber = seasonNumber)
                _seasonInfo.value = useCases.getTvSeason.getSeasonInfo()
                _seasonEpisodes.value = useCases.getTvSeason.getEpisodes()
            }
        }
    }

    fun getEpisode(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getTvSeason.getEpisode(id = id)
            _episodeToolbar.value = useCases.getTvSeason.getEpisodeToolbar()
            _episodeInfo.value = useCases.getTvSeason.getEpisodeInfo()
            _episodeCast.value = useCases.getTvSeason.getEpisodeCast()
            _episodeCrew.value = useCases.getTvSeason.getEpisodeCrew()
        }
    }



}