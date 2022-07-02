package com.kavrin.marvin.presentation.component

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.PagerState
import com.kavrin.marvin.ui.theme.fonts
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
            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
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
                        pagerState.scrollToPage(index)
                    }
                },
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