package com.kavrin.marvin.presentation.common

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
import com.kavrin.marvin.domain.model.common.Cast
import com.kavrin.marvin.domain.model.common.Crew
import com.kavrin.marvin.presentation.component.CreditItem
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun CastList(
    cast: List<Cast>,
    onCastClicked: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
        ) {

            Text(
                text = stringResource(R.string.cast),
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            
            items(
                items = cast,
                key = {
                    it.creditId
                }
            ) { cast ->
                CreditItem(
                    name = cast.name,
                    character = cast.character,
                    image = cast.profilePath,
                    creditId = cast.id,
                    onItemClicked = {
                        onCastClicked(it)
                    }
                )
            }
        }

    }
}

@Composable
fun CrewList(
    crew: List<Crew>,
    onCrewClicked: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
        ) {

            Text(
                text = stringResource(R.string.crew),
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = crew,
                key = {
                    it.creditId
                }
            ) { crew ->
                CreditItem(
                    name = crew.name,
                    character = crew.job,
                    image = crew.profilePath,
                    creditId = crew.id,
                    onItemClicked = {
                        onCrewClicked(it)
                    }
                )
            }
        }

    }
}