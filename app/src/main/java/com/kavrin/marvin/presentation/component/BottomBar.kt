package com.kavrin.marvin.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kavrin.marvin.R
import com.kavrin.marvin.navigation.BottomBarScreen
import com.kavrin.marvin.navigation.DurationConstants
import com.kavrin.marvin.ui.theme.bottomNavigationBackground
import com.kavrin.marvin.ui.theme.bottomNavigationContent

@Composable
fun BottomBar(navHostController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Library
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = bottomBarDestination,
        enter = expandVertically(
            animationSpec = tween(
                durationMillis = DurationConstants.SHORT,
                delayMillis = DurationConstants.MEDIUM + DurationConstants.LONG
            )
        )
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.bottomNavigationBackground,
            contentColor = MaterialTheme.colors.bottomNavigationContent,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navHostController
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(R.string.navigation_icon)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            if (screen.route != currentDestination?.route) {
                navController.popBackStack(
                    BottomBarScreen.Home.route,
                    inclusive = false,
                    saveState = true
                )
                navController.navigate(screen.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    )
}

