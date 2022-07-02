package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.domain.model.movie.api.Cast
import com.kavrin.marvin.domain.model.movie.api.Crew
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.screens.detail.component.*
import com.kavrin.marvin.ui.theme.*

@Composable
fun DetailsContent(
    isMovie: Boolean,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    movie: Movie? = null,
    tv: Tv? = null,
    cast: List<Cast>,
    crew: List<Crew>,
    genres: List<String>?
) {

    val date = if (isMovie) movie?.releaseDate else tv?.firstAirDate
    val adult = if (isMovie) movie?.adult else null
    val overview = if (isMovie) movie?.overview else tv?.overview
    val genresList = genres ?: emptyList()
    val rating = if (isMovie) movie?.voteAverage else tv?.voteAverage
    val voteCount = if (isMovie) movie?.voteCount else tv?.voteCount

    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier
            .background(color = MaterialTheme.colors.backGroundColor),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
    ) {

        item {
            Section1(
                date = date,
                adult = adult,
                genres = genresList,
                isMovie = isMovie,
                overview = overview
            )
        }

        item {
            Section2(
                rating = rating,
                voteCount = voteCount
            )
        }

        item {
            CastList(cast = cast)
        }

        item {
            CrewList(crew = crew)
        }
    }
}

@Composable
private fun Section1(
    date: String?,
    adult: Boolean?,
    genres: List<String>,
    isMovie: Boolean,
    overview: String?,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .padding(all = EXTRA_SMALL_PADDING)
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.cardColor
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = SMALL_PADDING)
        ) {

            date?.let {
                DateTimeAge(
                    date = it,
                    adult = adult
                )
            }

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Genres(
                genres = genres,
                isMovie = isMovie
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            overview?.let {
                Overview(
                    overview = it
                )
            }

        }

    }

}

@Composable
private fun Section2(
    rating: Double?,
    voteCount: Int?,
) {
    val rate = rating ?: 0.0
    val count = voteCount ?: 0

    Card(
        modifier = Modifier
            .padding(all = EXTRA_SMALL_PADDING)
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.cardColor
    ) {

        Rating(
            modifier = Modifier
                .padding(all = SMALL_PADDING),
            rating = rate,
            voteCount = count
        )

    }

}



@Preview
@Composable
fun Section1Preview() {
    Section1(
        date = "2022-02-02",
        adult = true,
        genres = listOf("Animation"),
        isMovie = false,
        overview = "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet"
    )
}