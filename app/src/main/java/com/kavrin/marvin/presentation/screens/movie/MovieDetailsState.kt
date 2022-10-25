package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.animation.core.MutableTransitionState
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.movie.UiMovie
import com.kavrin.marvin.presentation.component.TransitionState
import com.kavrin.marvin.util.UiText
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarState

data class MovieDetailsState(
    val ratingAnimation: MutableTransitionState<TransitionState> = MutableTransitionState(
        TransitionState.Start
    ),
    val fabAnimation: MutableTransitionState<TransitionState> = MutableTransitionState(
        TransitionState.Start
    ),
    val collapsingToolbar: CollapsingToolbarScaffoldState = CollapsingToolbarScaffoldState(
        CollapsingToolbarState()
    ),
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val movie: UiMovie? = null,
    val ratings: Map<String, String?> = emptyMap(),
    val ratingsError: UiText? = null,
    val isRatingsLoading: Boolean = true,
    val collectionOverview: String? = null,
    val collectionName: String? = null,
    val collectionBackdrop: String? = null,
    val collectionMovies: List<Movie>? = null,
    val collectionError: UiText? = null,
    val isCollectionLoading: Boolean? = null,
)