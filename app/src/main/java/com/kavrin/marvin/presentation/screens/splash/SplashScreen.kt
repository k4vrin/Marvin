package com.kavrin.marvin.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kavrin.marvin.R
import com.kavrin.marvin.navigation.BoardingScreens
import com.kavrin.marvin.navigation.Graph
import com.kavrin.marvin.presentation.component.LoadingAnimation
import com.kavrin.marvin.ui.theme.EXTRA_LARGE_PADDING
import com.kavrin.marvin.ui.theme.splashBackgroundBrush
import com.kavrin.marvin.ui.theme.splashFont
import com.kavrin.marvin.ui.theme.splashText

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val onBoardingState = splashViewModel.onBoardingCompleted.collectAsState()

    val animAlpha = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        animAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )

        navController.popBackStack()
        if (onBoardingState.value)
            navController.navigate(route = Graph.Home.route)
        else
            navController.navigate(route = BoardingScreens.Welcome.route)
    }

    Splash(animAlpha = animAlpha.value)
}

@Composable
fun Splash(animAlpha: Float = 1f) {

    //// Text Container ////
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = MaterialTheme.colors.splashBackgroundBrush)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        //// Text ////
        Text(
            modifier = Modifier
                .alpha(alpha = animAlpha),
            color = MaterialTheme.colors.splashText,
            text = stringResource(R.string.marvin),
            fontFamily = splashFont,
            fontWeight = FontWeight.Normal,
            fontSize = MaterialTheme.typography.h3.fontSize
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(EXTRA_LARGE_PADDING),
            horizontalArrangement = Arrangement.Center
        ) {

            LoadingAnimation()
        }
    }
}

@Preview
@Composable
fun SplashPrev() {
//    Splash()
}