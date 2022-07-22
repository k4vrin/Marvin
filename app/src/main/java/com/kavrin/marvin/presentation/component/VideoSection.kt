package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.common.Backdrop
import com.kavrin.marvin.domain.model.common.Video
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun VideoSection(
    trailer: Video?,
    trailerBackdrop: Backdrop?,
    videos: List<Video>,
    onItemClick: (String) -> Unit
) {


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
                text = stringResource(R.string.videos),
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )
        }

        if (trailer != null && trailerBackdrop != null) {
            TrailerItem(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = MEDIUM_PADDING),
                backdrop = trailerBackdrop.filePath,
                key = trailer.key,
                onPlayClicked = {
                    onItemClick(it)
                }
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = videos,
                key = {
                    it.id
                }
            ) { video ->

                VideoItem(
                    name = video.name,
                    key = video.key,
                    onCardClicked = {
                        onItemClick(it)
                    }
                )
            }

        }



    }
}