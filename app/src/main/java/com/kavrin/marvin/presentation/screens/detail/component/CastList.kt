package com.kavrin.marvin.presentation.screens.detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.movie.api.Cast
import com.kavrin.marvin.domain.model.movie.api.Crew
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.fonts

@Composable
fun CastList(
    cast: List<Cast>
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
        ) {

            Text(
                text = stringResource(R.string.cast),
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(all = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            
            items(
                items = cast,
                key = {
                    it.creditId
                }
            ) { cast ->
                CreditItem(name = cast.name, character = cast.character, image = cast.profilePath)
            }
        }

    }
}

@Composable
fun CrewList(
    crew: List<Crew>
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
        ) {

            Text(
                text = stringResource(R.string.crew),
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(all = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = crew,
                key = {
                    it.creditId
                }
            ) { crew ->
                CreditItem(name = crew.name, character = crew.job, image = crew.profilePath)
            }
        }

    }
}