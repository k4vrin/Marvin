package com.kavrin.marvin.presentation.screens.search

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.kavrin.marvin.navigation.Graph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val text by searchViewModel.searchQuery
    val searchType by searchViewModel.searchType
    val radioButtonState by searchViewModel.radioButtonState
    val isErrorVisible by searchViewModel.errorStatus
    val errorMessage by searchViewModel.errorMessage
    val movie = searchViewModel.movie.collectAsLazyPagingItems()
    val tv = searchViewModel.tv.collectAsLazyPagingItems()

    val gridState = rememberLazyGridState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        searchViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowErrorStatus -> {
                    searchViewModel.updateErrorStatus(showError = true, errorMessage = event.message)
                }
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            SearchTopBar(
                text = text,
                radioButtonState = radioButtonState,
                onTextChange = { searchViewModel.updateSearchQuery(it) },
                onSearchClicked = { query ->
                    searchViewModel.search(
                        query = query,
                        searchType = searchType
                    )
                },
                onCloseClicked = { navHostController.popBackStack() },
                onTypeClicked = { searchType ->
                    searchViewModel.updateRadioButton(searchType)
                    searchViewModel.updateSearchType(searchType)
                }
            )
        }
    ) {
        SearchContent(
            movie = movie,
            tv = tv,
            searchType = searchType,
            gridState = gridState,
            errorMessage = errorMessage,
            isErrorVisible = isErrorVisible,
            paddingValues = it,
            onCardClicked = { id ->
                when (searchType) {
                    is SearchType.MovieType -> navHostController.navigate(Graph.Movie.passId(id = id))
                    is SearchType.TvType -> navHostController.navigate(Graph.Tv.passId(id = id))
                    is SearchType.PersonType -> {}
                }
            },
            onMenuClicked = {}
        )
    }

}