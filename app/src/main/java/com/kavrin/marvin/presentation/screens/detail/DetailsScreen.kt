package com.kavrin.marvin.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.IMAGE_BASE_URL
import me.onebone.toolbar.*


@Composable
fun DetailScreen(
    navHostController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {

    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    SideEffect {
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }


    val movie by detailViewModel.selectedMovie.collectAsState()
    val tv by detailViewModel.selectedTv.collectAsState()
    val movieDetails by detailViewModel.movieDetails.collectAsState()
    val tvDetails by detailViewModel.tvDetails.collectAsState()

    var isMovie by remember {
        mutableStateOf(true)
    }
    isMovie = detailViewModel.isMovie ?: true

    val title = if (isMovie) movie?.title else tv?.name
    val backdrop = if (isMovie) movie?.backdropPath else tv?.backdropPath

    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            state = collapsingToolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                    ToolBar(
                        state = collapsingToolbarState,
                        backdrop = backdrop,
                        title = title,
                        onBackIconClicked = {
                            navHostController.popBackStack()
                        }
                    )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.backGroundColor)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {

                if (isMovie) {
                    movieDetails?.let { movieDetails ->
                        movie?.let { movie ->
                            MovieDetailsContent(movieDetails = movieDetails, movie = movie)
                        }
                    }
                }

            }

        }

        Divider(
            modifier = Modifier
                .offset(
                    y = with(LocalDensity.current) { collapsingToolbarState.toolbarState.height.toDp() }
                )
                .graphicsLayer {
                    alpha = 1f - collapsingToolbarState.toolbarState.progress
                },
            thickness = 2.dp
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset { IntOffset(x = 0, y = (collapsingToolbarState.toolbarState.height.toDp() - FAB_OFFSET).roundToPx()) }
                .padding(horizontal = SMALL_PADDING)
                .size(FAB_SIZE),
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
        start = 64.dp,
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
            .data("${IMAGE_BASE_URL}${backdrop}")
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
        fontFamily = fonts,
        fontSize = titleFontSize,
        fontWeight = FontWeight.SemiBold,
        color = textColor,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}