package com.kavrin.marvin.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kavrin.marvin.R

// Set of Material typography styles to start with
val Typography = Typography(
	body1 = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp
	)
	/* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val nunitoTypeFace = FontFamily(
	Font(R.font.nunito_black, weight = FontWeight.Black),
	Font(R.font.nunito_extra_bold, weight = FontWeight.ExtraBold),
	Font(R.font.nunito_semi_bold, weight = FontWeight.SemiBold),
	Font(R.font.nunito_bold, weight = FontWeight.Bold),
	Font(R.font.nunito_regular, weight = FontWeight.Normal),
	Font(R.font.nunito_extra_light, weight = FontWeight.ExtraLight)
)

val splashFont = FontFamily(
	Font(R.font.pacifico_regular, weight = FontWeight.Normal)
)