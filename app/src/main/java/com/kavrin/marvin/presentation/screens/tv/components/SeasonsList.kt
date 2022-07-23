package com.kavrin.marvin.presentation.screens.tv.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.ui.theme.*


@Composable
fun SeasonList(
    seasons: List<Season>,
    onSeasonClicked: (Int) -> Unit
) {

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .padding(horizontal = EXTRA_SMALL_PADDING),
        backgroundColor = MaterialTheme.colors.cardColor,
        onClick = { expandedState = !expandedState }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Seasons",
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.cardContentColor
                )

                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = { expandedState = !expandedState },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDow Arrow"
                    )
                }

            }

            AnimatedVisibility(
                visible = expandedState,
                enter = expandVertically(
                    expandFrom = Alignment.Top
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top
                )
            ) {
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                Column {
                    seasons.forEach { season ->

                        SeasonItem(
                            season = season,
                            onSeasonClicked = {
                                onSeasonClicked(it)
                            }
                        )

                        if (season != seasons.last()) {
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = EXTRA_SMALL_PADDING),
                                color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                            )
                        }

                    }
                }
            }

        }

    }

}


@Preview
@Composable
fun SeasonListPreview() {
    SeasonList(
        seasons = listOf(
            Season(
                airDate = "2019-07-25",
                episodeCount = 8,
                id = 7585,
                name = "Season 1",
                overview = "The even more intense, more insane season two finds The Boys on the run from the law, hunted by the Supes, and desperately trying to regroup and fight back against Vought. In hiding, Hughie, Mother’s Milk, Frenchie and Kimiko try to adjust to a new normal, with Butcher nowhere to be found. Meanwhile, Starlight must navigate her place in The Seven as Homelander sets his sights on taking complete control. His power is threatened with the addition of Stormfront, a social media-savvy new Supe, who has an agenda of her own. On top of that, the Supervillain threat takes center stage and makes waves as Vought seeks to capitalize on the nation’s paranoia.",
                posterPath = "/pccmXBaw0j7KZHO52lYLI93trSO.jpg",
                seasonNumber = 1
            ),
            Season(
                airDate = "2019-07-25",
                episodeCount = 8,
                id = 7585,
                name = "Season 1",
                overview = "The even more intense, more insane season two finds The Boys on the run from the law, hunted by the Supes, and desperately trying to regroup and fight back against Vought. In hiding, Hughie, Mother’s Milk, Frenchie and Kimiko try to adjust to a new normal, with Butcher nowhere to be found. Meanwhile, Starlight must navigate her place in The Seven as Homelander sets his sights on taking complete control. His power is threatened with the addition of Stormfront, a social media-savvy new Supe, who has an agenda of her own. On top of that, the Supervillain threat takes center stage and makes waves as Vought seeks to capitalize on the nation’s paranoia.",
                posterPath = "/pccmXBaw0j7KZHO52lYLI93trSO.jpg",
                seasonNumber = 1
            )
        ),
        onSeasonClicked = {}
    )
}

