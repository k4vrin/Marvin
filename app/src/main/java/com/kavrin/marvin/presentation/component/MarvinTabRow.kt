package com.kavrin.marvin.presentation.component

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.PagerState
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.ui.theme.tabIndicatorColor
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor


@Composable
fun MarvinTabRow(
	pagerState: PagerState,
	onTabClicked: (Int) -> Unit,
) {

	val titles = listOf("Movie", "Series")
	val indicator = @Composable { tabPositions: List<TabPosition> ->
		TabIndicator(
			color = tabIndicatorColor,
			modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
		)
	}


	TabRow(
		selectedTabIndex = pagerState.currentPage,
		backgroundColor = topBarBgColor,
		contentColor = topBarContentColor,
		indicator = indicator
	) {
		titles.forEachIndexed { index, title ->
			Tab(
				selected = pagerState.currentPage == index,
				onClick = { onTabClicked(index) },
				text = {
					Text(
						text = title,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.h6.fontSize,
						fontWeight = FontWeight.Bold
					)
				},
			)
		}

	}
}