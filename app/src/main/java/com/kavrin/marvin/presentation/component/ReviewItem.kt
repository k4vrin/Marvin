package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
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
    maxLines: Int = 7,
    onReviewClicked: (url: String) -> Unit
) {

    var cutText by remember(content) { mutableStateOf<String?>(null) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value
    val density = LocalDensity.current


    LaunchedEffect(content, textLayoutResult, seeMoreSize) {
        val lastLineIndex = maxLines - 1
        if (
            textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (charRect.left > textLayoutResult.size.width - seeMoreSize.width)
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = content.substring(startIndex = 0, endIndex = lastCharIndex)
        }

    }

    Card(
        modifier = Modifier
            .size(REVIEW_CARD_SIZE),
        onClick = {
            onReviewClicked(url)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = MaterialTheme.colors.cardGradientColor)
                .padding(start = SMALL_PADDING, end = SMALL_PADDING, top = SMALL_PADDING)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .weight(7f)
                ) {
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
                            contentDescription = stringResource(R.string.profile_pic),
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
                            color = MaterialTheme.colors.cardContentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        rate?.let { rate ->

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
                                    text = if (rate == 10.0) "10" else rate.toString(),
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
                        .weight(3f)
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

            Box {
                Text(
                    text = cutText ?: content,
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.Normal,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor,
                    onTextLayout = { textLayoutResultState.value = it },
                    lineHeight = 24.sp
                )

                val iconId = "inlineIcon"
                val text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.cardContentColor,
                            fontWeight = FontWeight.Normal,
                        )
                    ) {
                        append(text = "... ")
                    }

                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.fabBgColor,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    ) {
                        append(text = "Read more")
                        appendInlineContent(iconId, "[icon]")
                    }
                }
                val inlineContent = mapOf(
                    Pair(
                        first = iconId,
                        second = InlineTextContent(
                            placeholder = Placeholder(
                                width = 16.sp,
                                height = 16.sp,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.OpenInNew,
                                contentDescription = stringResource(R.string.open_new),
                                tint = MaterialTheme.colors.fabBgColor
                            )
                        }
                    )
                )

                Text(
                    text = text,
                    inlineContent = inlineContent,
                    onTextLayout = { seeMoreSizeState.value = it.size },
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    modifier = Modifier
                        .then(
                            if (seeMoreOffset != null)
                                Modifier.offset(
                                    x = with(density) { seeMoreOffset.x.toDp() },
                                    y = with(density) { seeMoreOffset.y.toDp() },
                                )
                            else
                                Modifier
                        )
                        .clickable {
                            onReviewClicked(url)
                        }
                        .alpha(if (seeMoreOffset != null) 1f else 0f)
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
        author = "Chris Sawin Sawin Sawin",
        rate = 4.1,
        date = "2022-07-05T13:08:30.584Z",
        content = "The God of Thunder returns in Marvel Studios’ “Thor: Love and Thunder” and audiences find that Thor (Chris Hemsworth), has been doing missions with the Guardians of the Galaxy while he works himself back into shape and looks to find a new purpose in life. sadas dasadddd addddd addddd addddd addddd addddd",
        profilePic = "",
        maxLines = 9
    )

}