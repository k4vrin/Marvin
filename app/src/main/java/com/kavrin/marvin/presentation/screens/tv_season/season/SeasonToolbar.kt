package com.kavrin.marvin.presentation.screens.tv_season.season

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.HOME_TOP_BAR_ELEVATION
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor
import com.kavrin.marvin.util.dateFormatter

@Composable
fun SeasonToolbar(
    title: String?,
    subtitle: String?,
    onBackClicked: () -> Unit,
    onMenuClicked: () -> Unit
) {
    TopAppBar(
        //// Title ////
        title = {
            Column {
                if (!title.isNullOrBlank()) {
                    Text(
                        text = title,
                        fontFamily = nunitoTypeFace,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.topBarContentColor
                    )
                }
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.disabled),
                        text = dateFormatter(date = subtitle),
                        fontFamily = nunitoTypeFace,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        color = MaterialTheme.colors.topBarContentColor
                    )

                }


            }
        },

        //// Background Color ////
        backgroundColor = MaterialTheme.colors.topBarBgColor,

        actions = {
            IconButton(
                onClick = onMenuClicked
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.menu_icon),
                    tint = MaterialTheme.colors.topBarContentColor
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = MaterialTheme.colors.topBarContentColor
                )
            }
        },
        elevation = HOME_TOP_BAR_ELEVATION
    )
}

@Preview
@Composable
fun SeasonToolbarPreview() {
    SeasonToolbar(
        title = "Season 1",
        subtitle = "2016-07-15",
        onBackClicked = {}
    ){}
}