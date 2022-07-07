package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.presentation.screens.detail.component.DateTime
import com.kavrin.marvin.presentation.screens.detail.component.Genres
import com.kavrin.marvin.presentation.screens.detail.component.Overview
import com.kavrin.marvin.ui.theme.*

@Composable
fun MovieDetailsContent(
    movieDetails: SingleMovieApiResponse,
    movie: Movie
) {

    Card(
        modifier = Modifier
            .padding(all = EXTRA_SMALL_PADDING)
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.cardColor
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = SMALL_PADDING)
        ) {

            DateTime(
                date = movie.releaseDate,
                time = movieDetails.runtime,
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = MEDIUM_PADDING),
                color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
            )

            Genres(
                genres = movieDetails.genres.map {
                    it.name
                },
                isMovie = true
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = MEDIUM_PADDING),
                color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
            )

            Overview(overview = movie.overview)

            Divider(
                modifier = Modifier
                    .padding(vertical = MEDIUM_PADDING),
                color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
            )
            
            LazyRow(
                userScrollEnabled = false
            ) {
                item {
                }
            }

        }

    }

}