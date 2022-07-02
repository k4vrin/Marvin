package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.rememberAsyncImagePainter
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.IMAGE_BASE_URL
import kotlin.math.roundToInt
import com.kavrin.marvin.util.lerp as marvinLerp

@Composable
fun CollapsingToolbar(
    progress: Float,
    backdrop: String,
    title: String,
    onBackButtonClicked: () -> Unit,
    onShareButtonClocked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(
        model = "${IMAGE_BASE_URL}${backdrop}",
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder)
    )

    val titleFontSize = lerp(
        MaterialTheme.typography.body1.fontSize,
        MaterialTheme.typography.h5.fontSize,
        progress
    )

    val textColor = lerp(
        start = MaterialTheme.colors.topBarContentColor,
        stop = Color.White,
        fraction = progress
    )

    val iconColor = lerp(
        start = MaterialTheme.colors.topBarContentColor,
        stop = Color.White,
        fraction = progress
    )

    val iconBackgroundColor = lerp(
        start = MaterialTheme.colors.topBarBgColor,
        stop = Color.Black.copy(alpha = 0.25f),
        fraction = progress
    )

    val textVerticalPad = lerp(
        start = EXTRA_SMALL_PADDING,
        stop = MEDIUM_PADDING,
        fraction = progress
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.6f)
        )
    )

    Box {

        Surface(
            color = MaterialTheme.colors.topBarBgColor,
            elevation = EXTRA_SMALL_PADDING,
            modifier = modifier
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.movie_poster),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            alpha = progress
                        }
                        .drawWithContent {
                            drawContent()
                            drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                        },
                    alignment = BiasAlignment(0f, 1f - ((1f - progress) * 0.75f))
                )

                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = SMALL_PADDING)
                        .fillMaxSize()
                ) {

                    CollapsingTollBarLayout(progress = progress) {

                        IconButton(
                            onClick = onBackButtonClicked,
                            modifier = Modifier
                                .padding(all = SMALL_PADDING)
                                .size(ICON_SIZE)
                                .background(
                                    color = iconBackgroundColor,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize(),
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.back_icon),
                                tint = iconColor
                            )
                        }

                        IconButton(
                            onClick = onShareButtonClocked,
                            modifier = Modifier
                                .padding(all = SMALL_PADDING)
                                .size(ICON_SIZE)
                                .background(
                                    color = iconBackgroundColor,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize(),
                                imageVector = Icons.Default.Share,
                                contentDescription = stringResource(R.string.share_icon),
                                tint = iconColor
                            )
                        }

                        Text(
                            modifier = Modifier
                                .padding(horizontal = SMALL_PADDING, vertical = textVerticalPad)
                                .fillMaxWidth(0.8F),
                            text = title,
                            fontFamily = fonts,
                            fontSize = titleFontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = SMALL_PADDING)
                .size(FAB_SIZE)
                .offset(y = FAB_OFFSET),
            onClick = { },
            backgroundColor = MaterialTheme.colors.fabBgColor,
            contentColor = Color.White
        ) {

            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .requiredSize(ICON_SIZE),
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_icon)
            )
        }
    }

}


@Composable
fun CollapsingTollBarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map {
            it.measure(constraints = constraints)
        }

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {

            val expandedHorizontalGuideline = (constraints.maxHeight * 0.4f).roundToInt()
            val collapsedHorizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()

            val backIcon = placeables[0]
            val shareIcon = placeables[1]
            val title = placeables[2]
//            val fab = placeables[3]

            backIcon.placeRelative(
                x = 0,
                y = marvinLerp(
                    start = collapsedHorizontalGuideline - backIcon.height / 2,
                    stop = backIcon.height / 2,
                    fraction = progress
                )
            )

            shareIcon.placeRelative(
                x = constraints.maxWidth - shareIcon.width,
                y = marvinLerp(
                    start = collapsedHorizontalGuideline - backIcon.height / 2,
                    stop = shareIcon.height / 2,
                    fraction = progress
                )
            )

            title.placeRelative(
                x = marvinLerp(
                    start = backIcon.width,
                    stop = 0,
                    fraction = progress
                ),
                y = marvinLerp(
                    start = collapsedHorizontalGuideline - title.height / 2,
                    stop = constraints.maxHeight - title.height,
                    fraction = progress
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CollapsingExpandedPreview() {
    MarvinTheme {
        CollapsingToolbar(
            progress = 1f,
            onBackButtonClicked = {},
            onShareButtonClocked = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            backdrop = "",
            title = ""
        )
    }
}

@Preview
@Composable
fun CollapsingHalfPreview() {
    MarvinTheme {
        CollapsingToolbar(
            progress = 0.5f,
            onBackButtonClicked = {},
            onShareButtonClocked = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            backdrop = "",
            title = ""
        )
    }
}

@Preview
@Composable
fun CollapsingCollapsedPreview() {
    MarvinTheme {
        CollapsingToolbar(
            progress = 0f,
            onBackButtonClicked = {},
            onShareButtonClocked = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            backdrop = "",
            title = ""
        )
    }
}




























