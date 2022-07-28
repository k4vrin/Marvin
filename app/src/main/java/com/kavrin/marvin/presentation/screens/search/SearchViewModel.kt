package com.kavrin.marvin.presentation.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.use_cases.search.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: SearchUseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchType = mutableStateOf<SearchType>(SearchType.MovieType)
    val searchType: State<SearchType> = _searchType

    private val _radioButtonState = mutableStateOf(
        RadioButtonState(
            movieType = true,
            tvType = false,
            personType = false
        )
    )
    val radioButtonState: State<RadioButtonState> = _radioButtonState

    private val _movie = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movie: StateFlow<PagingData<Movie>> = _movie

    private val _tv = MutableStateFlow<PagingData<Tv>>(PagingData.empty())
    val tv: StateFlow<PagingData<Tv>> = _tv

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorStatus = mutableStateOf(false)
    val errorStatus: State<Boolean> = _errorStatus

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSearchType(searchType: SearchType) {
        _searchType.value = searchType
    }

    fun updateErrorStatus(showError: Boolean, errorMessage: String) {
        viewModelScope.launch {
            if (showError) {
                _errorStatus.value = true
                _errorMessage.value = errorMessage
                delay(2000)
                _errorStatus.value = false
            }
        }
    }

    fun updateRadioButton(searchType: SearchType) {
        when (searchType) {
            is SearchType.MovieType -> _radioButtonState.value = radioButtonState.value.copy(
                movieType = true,
                tvType = false,
                personType = false
            )
            is SearchType.TvType -> _radioButtonState.value = radioButtonState.value.copy(
                movieType = false,
                tvType = true,
                personType = false
            )
            is SearchType.PersonType -> _radioButtonState.value = radioButtonState.value.copy(
                movieType = false,
                tvType = false,
                personType = true
            )
        }
    }

    fun search(query: String, searchType: SearchType) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                when (searchType) {
                    is SearchType.MovieType -> {
                        useCases.searchMovies(query = query).cachedIn(viewModelScope).collect {
                            _movie.value = it
                        }
                    }
                    is SearchType.TvType -> {
                        useCases.searchTvs(query = query).cachedIn(viewModelScope).collect {
                            _tv.value = it
                        }
                    }
                    is SearchType.PersonType -> {}
                }

            } catch (e: IllegalArgumentException) {
                _eventFlow.emit(
                    UiEvent.ShowErrorStatus(
                        message = e.message ?: "Couldn't find."
                    )
                )
            }
        }

    }

}

data class RadioButtonState(
    val movieType: Boolean,
    val tvType: Boolean,
    val personType: Boolean
)

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class ShowErrorStatus(val message: String) : UiEvent()
}