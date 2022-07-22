package com.kavrin.marvin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kavrin.marvin.navigation.SetupNavGraph
import com.kavrin.marvin.presentation.component.BottomBar
import com.kavrin.marvin.ui.theme.MarvinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		setContent {
			MarvinTheme {
				val navHostController = rememberAnimatedNavController()
				Scaffold(
					modifier = Modifier
					    .navigationBarsPadding(),
					bottomBar = { BottomBar(navHostController = navHostController) }
				) {
					SetupNavGraph(navHostController = navHostController, paddingValues = it)
				}
			}
		}
	}
}
