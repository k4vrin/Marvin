package com.kavrin.marvin.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.HOME_TOP_BAR_ELEVATION
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor

@Composable
fun HomeTopBar(
	onSearchClicked: () -> Unit,
) {

	TopAppBar(
		//// Title ////
		title = {
			Text(
				text = "Home",
				fontFamily = fonts,
				color = MaterialTheme.colors.topBarContentColor
			)
		},

		//// Background Color ////
		backgroundColor = MaterialTheme.colors.topBarBgColor,

		actions = {
			IconButton(
				onClick = onSearchClicked
			) {
				Icon(
					imageVector = Icons.Default.Search,
					contentDescription = stringResource(R.string.search_icon)
				)
			}
		},
		elevation = HOME_TOP_BAR_ELEVATION
	)
}

@Preview
@Composable
fun HomeTopBarPreview() {
	HomeTopBar {}
}