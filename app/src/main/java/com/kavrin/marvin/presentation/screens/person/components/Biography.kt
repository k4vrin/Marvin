package com.kavrin.marvin.presentation.screens.person.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.fabBgColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun Biography(
    bio: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 5
) {

    var cutText by remember(bio) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(bio, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (
            !expanded && textLayoutResult != null && seeMoreSize != null
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
            cutText = bio.substring(startIndex = 0, endIndex = lastCharIndex)
        }

    }
    Column {
        Box(
            modifier = modifier
                .animateContentSize()
        ) {

            Text(
                text = cutText ?: bio,
                maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResultState.value = it },
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.cardContentColor,
                lineHeight = 24.sp
            )

            if (!expanded) {
                val density = LocalDensity.current

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
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.drop_down_icon),
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
                            expanded = true
                            cutText = null
                        }
                        .alpha(if (seeMoreOffset != null) 1f else 0f)
                )
            }

        }

        if (expanded) {
            Text(
                modifier = Modifier
                    .padding(vertical = SMALL_PADDING)
                    .align(Alignment.End)
                    .clickable {
                        expanded = !expanded
                    },
                text = "Collapse",
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.fabBgColor,
            )

        }

    }


}