package com.kavrin.marvin.presentation.screens.person

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.kavrin.marvin.domain.model.person.PersonMovieCast
import com.kavrin.marvin.domain.model.person.PersonMovieCrew
import com.kavrin.marvin.domain.model.person.PersonTvCast
import com.kavrin.marvin.domain.model.person.PersonTvCrew
import com.kavrin.marvin.presentation.screens.person.components.Biography
import com.kavrin.marvin.presentation.screens.person.components.CreditList
import com.kavrin.marvin.presentation.screens.person.components.PersonInfo
import com.kavrin.marvin.ui.theme.*

@Composable
fun PersonContent(
    personInfo: Map<String, Int?>,
    personBio: String?,
    personMovieCast: List<PersonMovieCast>?,
    personMovieCrew: List<PersonMovieCrew>?,
    personTvCast: List<PersonTvCast>?,
    personTvCrew: List<PersonTvCrew>?,
    scrollState: ScrollState,
    movieCastLazyListState: LazyListState,
    movieCrewLazyListState: LazyListState,
    tvCastLazyListState: LazyListState,
    tvCrewLazyListState: LazyListState,
    onMovieClicked: (Int) -> Unit,
    onTvClicked: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Card(
            backgroundColor = MaterialTheme.colors.cardColor,
            shape = RectangleShape
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = SMALL_PADDING)
            ) {

                if (personInfo.isNotEmpty()) {
                    PersonInfo(
                        modifier = Modifier
                            .fillMaxWidth(),
                        personInfo = personInfo
                    )
                }

                if (!personBio.isNullOrBlank()) {

                    Divider(
                        modifier = Modifier
                            .padding(vertical = MEDIUM_PADDING),
                        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                    )

                    Biography(bio = personBio)

                }

            }

        }

        if (!personMovieCast.isNullOrEmpty()) {
            CreditList(
                movieCast = personMovieCast,
                lazyRowState = movieCastLazyListState,
                onItemClicked = onMovieClicked
            )
        }

        if (!personMovieCrew.isNullOrEmpty()) {
            CreditList(
                movieCrew = personMovieCrew,
                lazyRowState = movieCrewLazyListState,
                onItemClicked = onMovieClicked
            )
        }

        if (!personTvCast.isNullOrEmpty()) {
            CreditList(
                tvCast = personTvCast,
                tvCastLazyListState,
                onItemClicked = onTvClicked
            )
        }

        if (!personTvCrew.isNullOrEmpty()) {
            CreditList(
                tvCrew = personTvCrew,
                tvCrewLazyListState,
                onItemClicked = onTvClicked
            )
        }

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }
}