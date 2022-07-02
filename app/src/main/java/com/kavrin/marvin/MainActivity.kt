package com.kavrin.marvin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.kavrin.marvin.navigation.SetupNavGraph
import com.kavrin.marvin.ui.theme.MarvinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		setContent {
			MarvinTheme {
				val navHostController = rememberNavController()
				SetupNavGraph(navHostController = navHostController)
			}
		}
	}
}
