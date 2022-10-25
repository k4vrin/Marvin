package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.MarvinMovieItem
import com.kavrin.marvin.util.MarvinTvItem

@Composable
fun CardList(
    cardListTitle: String,
    listState: LazyListState,
    onSeeAllClicked: ((String) -> Unit)? = null,
    items: LazyListScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 360.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = cardListTitle,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

            if (onSeeAllClicked != null) {
                Text(
                    modifier = Modifier
                        .clickable {
                            onSeeAllClicked(cardListTitle)
                        },
                    text = stringResource(R.string.see_all),
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.contentColor
                )
            }
        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(all = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items()

            if (onSeeAllClicked != null) {
                item {
                    SeeAllCard {
                        onSeeAllClicked(cardListTitle)
                    }
                }
            }
        }
    }

}

@Composable
fun <T : MarvinMovieItem> MovieCardList(
    movies: LazyPagingItems<T>,
    listState: LazyListState,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
    onSeeAllClicked: ((String) -> Unit)? = null,
) {

    CardList(
        cardListTitle = when (movies.peek(0)) {
            is MovieAndTrending -> Constants.TRENDING
            is MovieAndPopular -> Constants.POPULAR
            is MovieAndTopRated -> Constants.TOP_RATED
            else -> ""
        },
        listState = listState,
        onSeeAllClicked = onSeeAllClicked
    ) {

        items(
            items = movies,
            key = {
                it.movie.movieId
            }
        ) { marvinMovieItem ->
            marvinMovieItem?.movie.let {

                MovieTvItem(
                    posterPath = it?.posterPath,
                    rating = it?.voteAverage,
                    voteCount = it?.voteCount,
                    itemId = it?.movieId,
                    itemTitle = it?.title,
                    releasedDate = it?.releaseDate,
                    onCardClicked = { id ->
                        onItemClicked(id)
                    },
                    onMenuIconClicked = { id ->
                        onMenuIconClicked(id)
                    }
                )
            }
        }

    }

}

@Composable
fun MovieCardList(
    movies: List<Movie>,
    listState: LazyListState,
    cardListTitle: String,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
    onSeeAllClicked: ((String) -> Unit)? = null,
) {

    CardList(
        cardListTitle = cardListTitle,
        listState = listState,
        onSeeAllClicked = onSeeAllClicked
    ) {

        items(
            items = movies,
            key = {
                it.movieId
            }
        ) { movie ->

            MovieTvItem(
                posterPath = movie.posterPath,
                rating = movie.voteAverage,
                voteCount = movie.voteCount,
                itemId = movie.movieId,
                itemTitle = movie.title,
                releasedDate = movie.releaseDate,
                onCardClicked = {
                    onItemClicked(it)
                },
                onMenuIconClicked = {
                    onMenuIconClicked(it)
                }
            )

        }

    }

}

@Composable
fun <T : MarvinTvItem> TvCardList(
    tvs: LazyPagingItems<T>,
    listState: LazyListState,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
    onSeeAllClicked: ((String) -> Unit)? = null,
) {

    CardList(
        cardListTitle = when (tvs.peek(0)) {
            is TvAndTrending -> Constants.TRENDING
            is TvAndPopular -> Constants.POPULAR
            is TvAndTopRated -> Constants.TOP_RATED
            else -> ""
        },
        listState = listState,
        onSeeAllClicked = onSeeAllClicked
    ) {

        items(
            items = tvs,
            key = {
                it.tv.tvId
            }
        ) { marvinMovieItem ->
            marvinMovieItem?.tv.let {

                MovieTvItem(
                    posterPath = it?.posterPath,
                    rating = it?.voteAverage,
                    voteCount = it?.voteCount,
                    itemId = it?.tvId,
                    itemTitle = it?.name,
                    releasedDate = it?.firstAirDate,
                    onCardClicked = { id ->
                        onItemClicked(id)
                    },
                    onMenuIconClicked = { id ->
                        onMenuIconClicked(id)
                    }
                )
            }
        }

    }

}

@Composable
fun TvCardList(
    tvs:List<Tv>,
    listState: LazyListState,
    cardListTitle: String,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
    onSeeAllClicked: ((String) -> Unit)? = null,
) {

    CardList(
        cardListTitle = cardListTitle,
        listState = listState,
        onSeeAllClicked = onSeeAllClicked
    ) {

        items(
            items = tvs,
            key = {
                it.tvId
            }
        ) { marvinMovieItem ->
            marvinMovieItem.let { tv ->

                MovieTvItem(
                    posterPath = tv.posterPath,
                    rating = tv.voteAverage,
                    voteCount = tv.voteCount,
                    itemId = tv.tvId,
                    itemTitle = tv.name,
                    releasedDate = tv.firstAirDate,
                    onCardClicked = {
                        onItemClicked(it)
                    },
                    onMenuIconClicked = {
                        onMenuIconClicked(it)
                    }
                )
            }
        }

    }

}