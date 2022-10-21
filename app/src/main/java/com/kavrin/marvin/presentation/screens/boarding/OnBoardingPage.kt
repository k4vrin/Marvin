package com.kavrin.marvin.presentation.screens.boarding

import androidx.annotation.DrawableRes
import com.kavrin.marvin.R

sealed class OnBoardingPage(
	@DrawableRes
	val image: Int,
	val description: String
) {
	object First : OnBoardingPage(
		image = R.drawable.batman,
		description = "Search through 1000+ movies and tv shows."
	)

	object Second : OnBoardingPage(
		image = R.drawable.figh_club,
		description = "Find your next favorite."
	)

	object Third : OnBoardingPage(
		image = R.drawable.marvin_og,
		description = ""
	)
}
