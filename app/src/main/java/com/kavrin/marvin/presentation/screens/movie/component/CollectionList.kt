package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.cardGradientColor
import com.kavrin.marvin.util.Constants.IMAGE_BACKDROP_BASE_URL

@Composable
fun CollectionList(
    collectionName: String?,
    collectionOverview: String?,
    collectionBackdrop: String?,
    movies: List<Movie>,
    onMovieClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {


    Box(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    ) {
        val gradient = MaterialTheme.colors.cardGradientColor

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = gradient,
                        blendMode = BlendMode.Multiply,
                        alpha = 0.5f
                    )
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data("${IMAGE_BACKDROP_BASE_URL}${collectionBackdrop}")
                .placeholder(R.drawable.placeholder_dark)
                .error(R.drawable.placeholder_dark)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.collection_backdrop),
            contentScale = ContentScale.Crop
        )

        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(all = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {

            item {
                if (!collectionName.isNullOrBlank()) {
                    CollectionHeader(
                        collectionName = collectionName,
                        collectionOverview = collectionOverview
                    )
                }
            }

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