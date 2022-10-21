package com.kavrin.marvin.presentation.screens.home.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kavrin.marvin.domain.use_cases.home.HomeUseCases
import com.kavrin.marvin.util.NetworkListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HomeUseCases,
    networkListener: NetworkListener
) : ViewModel() {

	private val _collapsingToolbar = mutableStateOf(
		CollapsingToolbarScaffoldState(
			CollapsingToolbarState()
		)
	)
	val collapsingToolbar: State<CollapsingToolbarScaffoldState> = _collapsingToolbar

	val getCarouselMovies = useCases.getCarouselMovies().cachedIn(viewModelScope)
	val getPopularMovies = useCases.getHomePopularMovies().cachedIn(viewModelScope)
	val getTopRatedMovies = useCases.getHomeTopRatedMovies().cachedIn(viewModelScope)
	val getTrendingMovies = useCases.getHomeTrendingMovies().cachedIn(viewModelScope)

	val getCarouselTvs = useCases.getCarouselTvs().cachedIn(viewModelScope)
	val getPopularTvs = useCases.getHomePopularTvs().cachedIn(viewModelScope)
	val getTopRatedTvs = useCases.getHomeTopRatedTvs().cachedIn(viewModelScope)
	val getTrendingTvs = useCases.getHomeTrendingTvs().cachedIn(viewModelScope)

    val isConnected = networkListener.checkNetworkAvailability()

    private val _error = mutableStateOf(HomeState.Error(isError = false))
    val error: State<HomeState.Error> = _error

    private val _loading = mutableStateOf(HomeState.Loading(isLoading = true))
    val loading: State<HomeState.Loading> = _loading

    private val _scrollState = mutableStateOf(ScrollState())
    val scrollState: State<ScrollState> = _scrollState

    fun updateScrollState(scrollState: ScrollState) {
        _scrollState.value = scrollState
    }

    fun updateError(
        isError: Boolean,
        message: String? = null
    ) {
        _error.value = error.value.copy(
            isError = isError,
            message = message
        )
    }

    fun updateLoading(
        isLoading: Boolean
    ) {
        _loading.value = _loading.value.copy(
            isLoading = isLoading
        )
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteAll()
        }
    }
}

sealed class HomeState {
    data class Loading(
        val isLoading: Boolean
    ) : HomeState()
    data class Error(
        val isError: Boolean,
        val message: String? = null
    ) : HomeState()
}

data class ScrollDetail(
    val index: Int,
    val offset: Int
)

data class ScrollState(
    val popularMovie: ScrollDetail = ScrollDetail(index = 0, offset = 0),
    val topRatedMovie: ScrollDetail = ScrollDetail(index = 0, offset = 0),
    val trendingMovie: ScrollDetail = ScrollDetail(index = 0, offset = 0),
    val popularTv: ScrollDetail = ScrollDetail(index = 0, offset = 0),
    val topRatedTv: ScrollDetail = ScrollDetail(index = 0, offset = 0),
    val trendingTv: ScrollDetail = ScrollDetail(index = 0, offset = 0),
)