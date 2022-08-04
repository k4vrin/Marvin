package com.kavrin.marvin.presentation.screens.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kavrin.marvin.domain.use_cases.person.PersonConstants
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.util.NetworkResult
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy

@Composable
fun PersonScreen(
    navHostController: NavHostController,
    personViewModel: PersonViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        personViewModel.getDetails()
    }

    val personRes by personViewModel.personRes.collectAsStateWithLifecycle()
    val personToolbar by personViewModel.personToolbar.collectAsStateWithLifecycle()
    val personInfo by personViewModel.personInfo.collectAsStateWithLifecycle()
    val personBio by personViewModel.personBio.collectAsStateWithLifecycle()
    val personMovieCast by personViewModel.personMovieCast.collectAsStateWithLifecycle()
    val personMovieCrew by personViewModel.personMovieCrew.collectAsStateWithLifecycle()
    val personTvCast by personViewModel.personTvCast.collectAsStateWithLifecycle()
    val personTvCrew by personViewModel.personTvCrew.collectAsStateWithLifecycle()
    val collapsingToolbarState by personViewModel.collapsingToolbar


    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handlePersonNetworkResult(
        details = personRes,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            personViewModel.getDetails()
            isRefreshing = false
        }
    )


    val scrollState = rememberScrollState()
    val movieCastLazyRow = rememberLazyListState()
    val movieCrewLazyRow = rememberLazyListState()
    val tvCrewLazyRow = rememberLazyListState()
    val tvCastLazyRow = rememberLazyListState()


    if (result) {

        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize(),
            state = collapsingToolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                PersonToolbar(
                    state = collapsingToolbarState,
                    poster = personToolbar[PersonConstants.POSTER],
                    name = personToolbar[PersonConstants.NAME],
                    birthdate = personToolbar[PersonConstants.BIRTH_DATE],
                    onBackIconClicked = { navHostController.popBackStack() },
                    onShareClicked = { /*TODO*/ },
                )
            }
        ) {

            PersonContent(
                personInfo = personInfo,
                personBio = personBio,
                personMovieCast = personMovieCast,
                personMovieCrew = personMovieCrew,
                personTvCast = personTvCast,
                personTvCrew = personTvCrew,
                scrollState = scrollState,
                movieCastLazyListState = movieCastLazyRow,
                movieCrewLazyListState = movieCrewLazyRow,
                tvCastLazyListState = tvCastLazyRow,
                tvCrewLazyListState = tvCrewLazyRow,
                onMovieClicked = {
                    navHostController.navigate(Graph.Movie.passId(it))
                },
                onTvClicked = {
                    navHostController.navigate(Graph.Tv.passId(it))
                }
            )

        }

    }

}

@Composable
private fun handlePersonNetworkResult(
    details: NetworkResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when (details) {
        is NetworkResult.Error -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = details.message,
                onRefresh = onRefresh
            )
            false
        }
        is NetworkResult.Loading -> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        is NetworkResult.Success -> true
    }
}