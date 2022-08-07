package com.kavrin.marvin.presentation.screens.tv_season.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kavrin.marvin.domain.use_cases.tv_season.EpisodeSummary
import com.kavrin.marvin.presentation.screens.tv_season.component.EpisodeItem
import com.kavrin.marvin.presentation.screens.tv_season.component.SeasonHeader
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun SeasonContent(
    lazyListState: LazyListState,
    episodes: List<EpisodeSummary>,
    seasonName: String?,
    seasonOverview: String?,
    seasonPoster: String?,
    onEpisodeClicked: (Int) -> Unit
) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor),
        state = lazyListState,
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        item {
            SeasonHeader(
                poster = seasonPoster,
                seasonName = seasonName,
                seasonOverview = seasonOverview
            )
        }

        items(
            items = episodes,
            key = {
                it.id
            }
        ) { episode ->
            EpisodeItem(
                episodeSummary = episode,
                onEpisodeClicked = onEpisodeClicked
            )
        }

    }

}