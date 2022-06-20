package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TabIndicator(
	color: Color,
	modifier: Modifier = Modifier,
) {
	Spacer(
		modifier
			.padding(horizontal = 24.dp)
			.height(4.dp)
			.background(
				color = color,
				shape = RoundedCornerShape(
					topStartPercent = 100, topEndPercent = 100
				)
			)
	)
}