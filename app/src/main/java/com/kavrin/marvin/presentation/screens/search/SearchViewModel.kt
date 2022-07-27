package com.kavrin.marvin.presentation.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.search.InvalidQueryException
import com.kavrin.marvin.domain.use_cases.search.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
                    is SearchType.TvType -> {}
                    is SearchType.PersonType -> {}
                }

            } catch (e: InvalidQueryException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
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
}