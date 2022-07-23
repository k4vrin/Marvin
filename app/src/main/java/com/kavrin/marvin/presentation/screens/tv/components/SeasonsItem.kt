package com.kavrin.marvin.presentation.screens.tv.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.dateFormatter


@Composable
fun SeasonItem(
    season: Season,
    onSeasonClicked: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                onSeasonClicked(season.id)
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_POSTER_BASE_URL}${season.posterPath}")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.episode_pic),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.width(SMALL_PADDING))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(7f, fill = true)
        ) {

            val title = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.cardContentColor
                    )
                ) {
                    append(text = season.name)
                }

                withStyle(
                    style = SpanStyle(
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.cardContentColor
                    )
                ) {
                    append(text = " (Episodes: ${season.episodeCount})")
                }
            }

            Text(
                text = title,
            )

            if (season.overview.isNotBlank()) {

                Spacer(modifier = Modifier.height(SMALL_PADDING))

                Text(
                    text = season.overview,
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor
                )
            }



            if (!season.airDate.isNullOrBlank()) {

                Spacer(modifier = Modifier.height(EXTRA_SMALL_PADDING))

                Text(
                    text = dateFormatter(date = season.airDate),
                    color = MaterialTheme.colors.cardContentColor,
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }


        }


    }

}

@Preview(showBackground = true)
@Composable
fun SeasonItemPrev() {
    SeasonItem(
        season = Season(
            airDate = "2019-07-25",
            episodeCount = 8,
            id = 7585,
            name = "Season 1",
            overview = "The even more intense, more insane season two finds The Boys on the run from the law, hunted by the Supes, and desperately trying to regroup and fight back against Vought. In hiding, Hughie, Mother’s Milk, Frenchie and Kimiko try to adjust to a new normal, with Butcher nowhere to be found. Meanwhile, Starlight must navigate her place in The Seven as Homelander sets his sights on taking complete control. His power is threatened with the addition of Stormfront, a social media-savvy new Supe, who has an agenda of her own. On top of that, the Supervillain threat takes center stage and makes waves as Vought seeks to capitalize on the nation’s paranoia.",
            posterPath = "/pccmXBaw0j7KZHO52lYLI93trSO.jpg",
            seasonNumber = 1
        ),
        onSeasonClicked = {}
    )
}
