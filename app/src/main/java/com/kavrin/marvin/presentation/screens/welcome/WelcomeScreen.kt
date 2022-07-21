package com.kavrin.marvin.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.R
import com.kavrin.marvin.navigation.Graph
import com.kavrin.marvin.presentation.component.WormHorizontalPagerIndicator
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.LAST_WELCOME_PAGE
import com.kavrin.marvin.util.Constants.ON_BOARDING_PAGE_COUNT

@Composable
fun WelcomeScreen(
	navController: NavHostController,
	welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
	//// List Of Pages ////
	val pages = listOf(
		OnBoardingPage.First,
		OnBoardingPage.Second
	)
	//// Pager State ////
	val pagerState = rememberPagerState()

	//// Pager Container ////
	Box {

		//// Horizontal Pager ////
		HorizontalPager(
			state = pagerState,
			count = ON_BOARDING_PAGE_COUNT
		) { position ->
			//// Pager Screen ////
			PagerScreen(onBoardingPage = pages[position])
		}

		//// Indicator and Button Container ////
		Column(
			modifier = Modifier
				.fillMaxSize()
				.systemBarsPadding(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Bottom
		) {

			//// Indicator ////
//			HorizontalPagerIndicator(
//				pagerState = pagerState,
//				activeColor = BrightMaroon,
//				inactiveColor = LightGray,
//				indicatorWidth = INDICATOR_WIDTH,
//				spacing = INDICATOR_SPACING
//			)

			WormHorizontalPagerIndicator(
				pagerState = pagerState,
				activeColor = BrightMaroon,
				indicatorWidth = INDICATOR_WIDTH,
				spacing = INDICATOR_SPACING
			)

			//// Button ////
			ExploreButton(
				pagerState = pagerState,
				onClick = {
					welcomeViewModel.saveOnBoardingState(completed = true)
					navController.popBackStack()
					navController.navigate(route = Graph.Home.route)
				}
			)
		}
	}

}

@Composable
fun PagerScreen(
	onBoardingPage: OnBoardingPage,
) {

	Box {

		//// Background Image ////
		Image(
			modifier = Modifier
				.fillMaxSize(),
			painter = painterResource(id = onBoardingPage.image),
			contentScale = ContentScale.Crop,
			contentDescription = stringResource(R.string.on_boarding_image)
		)

		//// Text Container ////
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(
					brush = Brush.verticalGradient(
						listOf(
							Color.Black.copy(alpha = 0.3f),
							Color.Black.copy(alpha = 0.7f)
						)
					)
				),
			verticalArrangement = Arrangement.Bottom
		) {
			//// Text ////
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = MEDIUM_PADDING),
				text = onBoardingPage.description,
				color = Color.White,
				fontFamily = nunitoTypeFace,
				fontSize = MaterialTheme.typography.h5.fontSize,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center
			)
			//// Bottom Gap ////
			Spacer(modifier = Modifier.height(WELCOME_BOTTOM_GAP))
		}
	}
}

@Composable
fun ExploreButton(
	pagerState: PagerState,
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
) {

	//// Button Container ////
	Row(
		modifier = modifier
			.padding(horizontal = EXTRA_LARGE_PADDING)
			.height(WELCOME_ROW_HEIGHT),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {

		//// Animated Container ////
		AnimatedVisibility(
			visible = pagerState.currentPage == LAST_WELCOME_PAGE
		) {
			//// Button ////
			Button(
				modifier = Modifier
					.fillMaxWidth()
					.height(WELCOME_BUTTON_HEIGHT),
				onClick = onClick,
				colors = ButtonDefaults.buttonColors(
					backgroundColor = BrightMaroon,
					contentColor = Color.White
				)
			) {
				//// Text In Button ////
				Text(
					text = stringResource(R.string.explore),
					fontFamily = nunitoTypeFace,
					fontWeight = FontWeight.SemiBold,
					fontSize = MaterialTheme.typography.subtitle1.fontSize,
					textAlign = TextAlign.Center
				)
			}
		}

	}
}


@Preview
@Composable
fun PagerScreenPreview() {
	PagerScreen(onBoardingPage = OnBoardingPage.Second)
}

@Preview
@Composable
fun WelcomeScreenPreview() {
	WelcomeScreen(navController = rememberNavController())
}