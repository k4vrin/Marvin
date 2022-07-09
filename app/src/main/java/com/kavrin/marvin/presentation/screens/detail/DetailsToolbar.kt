package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope

@Composable
fun CollapsingToolbarScope.ToolBar(
    state: CollapsingToolbarScaffoldState,
    backdrop: String?,
    title: String?,
    onBackIconClicked: () -> Unit
) {

    val iconColor = lerp(
        start = MaterialTheme.colors.topBarContentColor,
        stop = Color.White,
        fraction = state.toolbarState.progress
    )

    val iconBackgroundColor = lerp(
        start = MaterialTheme.colors.topBarBgColor,
        stop = Color.Black.copy(alpha = 0.25f),
        fraction = state.toolbarState.progress
    )

    val textColor = lerp(
        start = MaterialTheme.colors.topBarContentColor,
        stop = Color.White,
        fraction = state.toolbarState.progress
    )

    val titleFontSize = androidx.compose.ui.unit.lerp(
        MaterialTheme.typography.body1.fontSize,
        MaterialTheme.typography.h6.fontSize,
        state.toolbarState.progress
    )

    val textVerticalPad = androidx.compose.ui.unit.lerp(
        start = MIN_TOOLBAR,
        stop = EXTRA_LARGE_PADDING,
        fraction = state.toolbarState.progress
    )

    val textHorizontalPad = androidx.compose.ui.unit.lerp(
        start = 80.dp,
        stop = MEDIUM_PADDING,
        fraction = state.toolbarState.progress
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.6f)
        )
    )

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${Constants.IMAGE_BASE_URL}${backdrop}")
            .placeholder(R.drawable.placeholder_dark)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(id = R.string.movie_poster),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .background(color = MaterialTheme.colors.statusBarColor)
            .fillMaxWidth()
            .height(300.dp)
            .parallax(0.5f)
            .graphicsLayer {
                alpha = state.toolbarState.progress
            }
            .drawWithContent {
                drawContent()
                drawRect(brush = gradient, blendMode = BlendMode.Multiply)
            },
    )

    IconButton(
        onClick = onBackIconClicked,
        modifier = Modifier
            .road(
                whenExpanded = Alignment.TopStart,
                whenCollapsed = Alignment.CenterStart
            )
            .padding(
                top = textVerticalPad,
                start = LARGE_PADDING,
                bottom = MIN_TOOLBAR
            )
            .background(
                color = iconBackgroundColor,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back_icon),
            tint = iconColor
        )
    }

    IconButton(
        onClick = {},
        modifier = Modifier
            .road(
                whenExpanded = Alignment.TopEnd,
                whenCollapsed = Alignment.CenterEnd
            )
            .padding(
                top = textVerticalPad,
                end = MEDIUM_PADDING,
                bottom = MIN_TOOLBAR
            )
            .background(
                color = iconBackgroundColor,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(R.string.share_icon),
            tint = iconColor
        )
    }

    Text(
        modifier = Modifier
            .road(
                whenExpanded = Alignment.BottomStart,
                whenCollapsed = Alignment.CenterStart
            )
            .padding(horizontal = textHorizontalPad, vertical = textVerticalPad)
            .fillMaxWidth(),
        text = title ?: "",
        fontFamily = nunitoTypeFace,
        fontSize = titleFontSize,
        fontWeight = FontWeight.SemiBold,
        color = textColor,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}