package com.kavrin.marvin.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kavrin.marvin.ui.theme.*

@Composable
fun SplashScreen(navController: NavHostController) {

	val animAlpha = remember { Animatable(initialValue = 0f) }
	LaunchedEffect(key1 = true) {
		animAlpha.animateTo(
			targetValue = 1f,
			animationSpec = tween(
				durationMillis = 1000,
				delayMillis = 200
			)
		)
	}

	Splash(animAlpha = animAlpha.value)
}

@Composable
fun Splash(animAlpha: Float = 1f) {
	if (isSystemInDarkTheme()) {
		Box(
			modifier = Modifier
				.background(color = Color.Black)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Text(
				modifier = Modifier
					.alpha(alpha = animAlpha),
				color = Color.White,
				text = "Marvin",
				fontFamily = splashFont,
				fontWeight = FontWeight.Normal,
				fontSize = 40.sp
			)
		}
	} else {
		Box(
			modifier = Modifier
				.background(
					brush = Brush.verticalGradient(colors = listOf(SilverPink, AntiqueBrass))
				)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Text(
				modifier = Modifier
					.alpha(alpha = animAlpha),
				color = BrightMaroon,
				text = "Marvin",
				fontFamily = splashFont,
				fontWeight = FontWeight.Normal,
				fontSize = 40.sp
			)
		}
	}

}

@Preview
@Composable
fun SplashPrev() {
	Splash()
}