package com.kavrin.marvin.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.util.Constants


@Composable
fun CarouselItem(
	posterPath: String?,
	rating: Double?,
	voteCount: Int?,
	itemId: Int?,
	scale: Float,
	modifier: Modifier = Modifier,
	onItemClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit,
) {
	val painter = rememberAsyncImagePainter(
		model = "${Constants.IMAGE_BASE_URL}${posterPath}",
		placeholder = painterResource(id = R.drawable.placeholder),
		error = painterResource(id = R.drawable.placeholder)
	)

	Column(
		modifier = modifier
			.padding(horizontal = 5.dp)
	) {
		PosterWithRating(
			modifier = Modifier
				.height(300.dp)
				.width(200.dp)
				.scale(scale)
				.clip(shape = RoundedCornerShape(EXTRA_SMALL_PADDING)),
			painter = painter,
			rating = rating,
			count = voteCount,
			itemId = itemId,
			onMenuIconClicked = onMenuIconClicked,
			onItemClicked = onItemClicked
		)
	}
}
