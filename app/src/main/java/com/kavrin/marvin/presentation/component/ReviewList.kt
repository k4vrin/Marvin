package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.common.Review
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun ReviewList(
    lazyRowState: LazyListState,
    reviews: List<Review>,
    onReviewClicked: (String) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
        ) {

            Text(
                text = stringResource(R.string.reviews),
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )

        }

        LazyRow(
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = reviews,
                key = {
                    it.id
                }
            ) { review ->

                ReviewItem(
                    author = review.author,
                    rate = review.authorDetails.rating,
                    date = review.updatedAt,
                    profilePic = review.authorDetails.avatarPath,
                    content = review.content,
                    url = review.url,
                    onReviewClicked = {
                        onReviewClicked(it)
                    }
                )

            }

        }


    }
}