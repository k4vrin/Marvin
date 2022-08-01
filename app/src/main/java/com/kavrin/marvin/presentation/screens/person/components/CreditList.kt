package com.kavrin.marvin.presentation.screens.person.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.kavrin.marvin.domain.model.person.PersonMovieCast
import com.kavrin.marvin.domain.model.person.PersonMovieCrew
import com.kavrin.marvin.domain.model.person.PersonTvCast
import com.kavrin.marvin.domain.model.person.PersonTvCrew
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING

@JvmName("PersonTvCast")
@Composable
fun CreditList(
    tvCast: List<PersonTvCast>,
    lazyRowState: LazyListState,
    onItemClicked: (Int) -> Unit
) {

    LazyRow(
        state = lazyRowState,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        stickyHeader {
            PersonStickyHeader(title = "Cast")
        }

        items(
            items = tvCast,
            key = {
                it.creditId
            }
        ) { tvCast ->
            PersonCreditItem(
                posterPath = tvCast.posterPath,
                itemId = tvCast.id,
                rating = tvCast.voteAverage,
                voteCount = tvCast.voteCount,
                itemTitle = tvCast.name,
                subText = tvCast.character,
                episodeCount = tvCast.episodeCount,
                onItemClicked = onItemClicked,
                onMenuIconClicked = onItemClicked
            )
        }
    }


}

@JvmName("PersonTvCrew")
@Composable
fun CreditList(
    tvCrew: List<PersonTvCrew>,
    lazyRowState: LazyListState,
    onItemClicked: (Int) -> Unit
) {

    LazyRow(
        state = lazyRowState,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        stickyHeader {
            PersonStickyHeader(title = "Crew")
        }

        items(
            items = tvCrew,
            key = {
                it.creditId
            }
        ) { tvCrew ->
            PersonCreditItem(
                posterPath = tvCrew.posterPath,
                itemId = tvCrew.id,
                rating = tvCrew.voteAverage,
                voteCount = tvCrew.voteCount,
                itemTitle = tvCrew.name,
                subText = tvCrew.job,
                episodeCount = tvCrew.episodeCount,
                onItemClicked = onItemClicked,
                onMenuIconClicked = onItemClicked
            )
        }
    }

}

@JvmName("PersonMovieCast")
@Composable
fun CreditList(
    movieCast: List<PersonMovieCast>,
    lazyRowState: LazyListState,
    onItemClicked: (Int) -> Unit
) {

    LazyRow(
        state = lazyRowState,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        stickyHeader {
            PersonStickyHeader(title = "Cast")
        }

        items(
            items = movieCast,
            key = {
                it.creditId
            }
        ) { movieCast ->
            PersonCreditItem(
                posterPath = movieCast.posterPath,
                itemId = movieCast.id,
                rating = movieCast.voteAverage,
                voteCount = movieCast.voteCount,
                itemTitle = movieCast.title,
                subText = movieCast.character,
                onItemClicked = onItemClicked,
                onMenuIconClicked = onItemClicked
            )
        }
    }

}

@JvmName("PersonMovieCrew")
@Composable
fun CreditList(
    movieCrew: List<PersonMovieCrew>,
    lazyRowState: LazyListState,
    onItemClicked: (Int) -> Unit
) {

    LazyRow(
        state = lazyRowState,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        stickyHeader {
            PersonStickyHeader(title = "Crew")
        }

        items(
            items = movieCrew,
            key = {
                it.creditId
            }
        ) { movieCrew ->
            PersonCreditItem(
                posterPath = movieCrew.posterPath,
                itemId = movieCrew.id,
                rating = movieCrew.voteAverage,
                voteCount = movieCrew.voteCount,
                itemTitle = movieCrew.title,
                subText = movieCrew.job,
                onItemClicked = onItemClicked,
                onMenuIconClicked = onItemClicked
            )
        }
    }

}