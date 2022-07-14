package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun MovieCardList(
    cardListTitle: String,
    items: List<Movie>,
    onMovieClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
        ) {

            Text(
                text = cardListTitle,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )
        }

        LazyRow(
            contentPadding = PaddingValues(all = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = items,
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
                        onMovieClicked(it)
                    },
                    onMenuIconClicked = {
                        onMenuClicked(it)
                    }
                )

            }
        }


    }


}