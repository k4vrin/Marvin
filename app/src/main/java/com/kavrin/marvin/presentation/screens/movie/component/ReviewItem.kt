package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ReviewItem(
    author: String,
    rate: Double?,
    date: String,
    profilePic: String?,
    content: String,
    url: String,
    onReviewClicked: (url: String) -> Unit
) {


    Card(
        modifier = Modifier
            .size(REVIEW_CARD_SIZE),
        onClick = {
            onReviewClicked(url)
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = MaterialTheme.colors.cardGradientColor)
                .padding(start = SMALL_PADDING, end = SMALL_PADDING, top = SMALL_PADDING)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row {
                        Surface(
                            modifier = Modifier
                                .size(REVIEW_PROFILE_IMAGE_SIZE),
                            shape = CircleShape,
                            border = BorderStroke(
                                width = XX_SMALL_PADDING,
                                color = MaterialTheme.colors.cardContentColor
                            )
                        ) {

                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(profilePic?.drop(1))
                                    .placeholder(R.drawable.ic_profile_placeholder)
                                    .error(R.drawable.ic_profile_placeholder)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(R.string.pofile_pic),
                                contentScale = ContentScale.Crop
                            )

                        }

                        Spacer(modifier = Modifier.width(SMALL_PADDING))

                        Column(
                            modifier = Modifier
                                .height(REVIEW_PROFILE_IMAGE_SIZE),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = author,
                                fontFamily = nunitoTypeFace,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.body2.fontSize,
                                color = MaterialTheme.colors.cardContentColor
                            )

                            if (rate != null) {

                                val color = when (rate) {
                                    in 0.0..4.0 -> LowRate
                                    in 4.0..7.0 -> MediumRate
                                    else -> HighRate
                                }

                                Spacer(modifier = Modifier.height(EXTRA_SMALL_PADDING))

                                Surface(
                                    modifier = Modifier
                                        .height(MEDIUM_PADDING)
                                        .width(LARGE_PADDING),
                                    shape = RoundedCornerShape(size = XX_SMALL_PADDING),
                                    color = color
                                ) {

                                    Text(
                                        text = rate.toString(),
                                        fontFamily = nunitoTypeFace,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = MaterialTheme.typography.caption.fontSize,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black
                                    )

                                }

                            }

                        }
                    }

                    Row(
                        modifier = Modifier
                            .width(REVIEW_DATE_WIDTH),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = reviewDateFormatter(date = date),
                            fontFamily = nunitoTypeFace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.overline.fontSize,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.cardContentColor
                        )
                    }

                }

                Spacer(modifier = Modifier.height(SMALL_PADDING))

                Text(
                    text = content,
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.Normal,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor
                )

            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    onReviewClicked(url)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = stringResource(R.string.open_new),
                    tint = MaterialTheme.colors.cardContentColor
                )
            }

        }

    }
}

@Composable
fun reviewDateFormatter(date: String): String {
    val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formatPattern = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
    return LocalDateTime.parse(date, parsePattern).format(formatPattern)
}

@Preview
@Composable
fun ReviewItemPreview() {
    ReviewItem(
        url = "",
        onReviewClicked = {},
        author = "Chris Sawin",
        rate = 4.1,
        date = "2022-07-05T13:08:30.584Z",
        content = "The God of Thunder returns in Marvel Studios’ “Thor: Love and Thunder” and audiences find that Thor (Chris Hemsworth), has been doing missions with the Guardians of the Galaxy while he works himself back into shape and looks to find a new purpose in life.sadasdasadaddddd",
        profilePic = ""
    )
}