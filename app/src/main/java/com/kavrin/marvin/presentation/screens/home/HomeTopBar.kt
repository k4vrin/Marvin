package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.ConnectivityStatus
import com.kavrin.marvin.ui.theme.HOME_TOP_BAR_ELEVATION
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor

@Composable
fun HomeTopBar(
	isConnected: Boolean,
	onSearchClicked: () -> Unit,
) {

	Column {
		ConnectivityStatus(isConnected = isConnected)

		TopAppBar(
			//// Title ////
			title = {
				Text(
					text = stringResource(R.string.home),
					fontFamily = nunitoTypeFace,
					fontWeight = FontWeight.Bold,
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
						contentDescription = stringResource(R.string.search_icon),
						tint = MaterialTheme.colors.topBarContentColor
					)
				}
			},
			navigationIcon = {
				IconButton(
					onClick = onSearchClicked
				) {
					Icon(
						imageVector = Icons.Default.Menu,
						contentDescription = stringResource(R.string.search_icon),
						tint = MaterialTheme.colors.topBarContentColor
					)
				}
			},
			elevation = HOME_TOP_BAR_ELEVATION
		)
	}
}

@Preview
@Composable
fun HomeTopBarPreview() {
//	HomeTopBar(rememberCollapsingToolbarScaffoldState()) {}
}