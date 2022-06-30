package com.kavrin.marvin.presentation.screens.detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.util.movieGenresIcon
import com.kavrin.marvin.util.tvGenresIcon

@Composable
fun Genres(
    genres: List<String>?,
    isMovie: Boolean,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(
            text = "Genres",
            fontFamily = fonts,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

        genres?.windowed(size = 2, step = 2, partialWindows = true)?.forEach { listGenre ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                listGenre.forEach { genre ->
                    val painter = painter(isMovie = isMovie, genre = genre)
                    ///// Each Genre Container /////
                    Row(
                        modifier = Modifier
                            .width(width = 180.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ///// Genre Icon /////
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painter,
                            contentDescription = "Genre Icon",
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ///// Genre Name /////
                        Text(
                            text = genre,
                            fontFamily = fonts,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun painter(
    isMovie: Boolean,
    genre: String
): Painter {

    return if (isMovie) {
        painterResource(id = movieGenresIcon[genre] ?: R.drawable.documentary)
    } else {
        painterResource(id = tvGenresIcon[genre] ?: R.drawable.documentary)
    }

}


@Preview
@Composable
fun GenresPreview() {
    Genres(
        genres = listOf(
            "Action & Adventure",
            "Sci-Fi & Fantasy",
            "War & Politics",
        ),
        isMovie = false
    )
}