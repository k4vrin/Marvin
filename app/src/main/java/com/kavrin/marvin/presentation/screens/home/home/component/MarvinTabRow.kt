package com.kavrin.marvin.presentation.screens.home.home.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.ui.theme.tabIndicatorColor
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor
import kotlinx.coroutines.launch


@Composable
fun MarvinTabRow(
    pagerState: PagerState
) {

    val scope = rememberCoroutineScope()
    val titles = listOf("Movie", "Series")
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabIndicator(
            color = MaterialTheme.colors.tabIndicatorColor,
            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
        )
    }


    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.topBarBgColor,
        contentColor = MaterialTheme.colors.topBarContentColor,
        indicator = indicator
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = title,
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }

    }
}