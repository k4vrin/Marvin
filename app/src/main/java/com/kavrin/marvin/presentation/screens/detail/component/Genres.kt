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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.cardContentColor
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
                            tint = MaterialTheme.colors.cardContentColor
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ///// Genre Name /////
                        Text(
                            text = genre,
                            fontFamily = fonts,
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.cardContentColor
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