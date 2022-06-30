package com.kavrin.marvin.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val BackGroundLight = Color(0xFFF5F5F5)
val BackGroundDark = Color(0xFF090909)

val BrightMaroon = Color(0xFFB9314F)
val AntiqueBrass = Color(0xFFD5A18E)
val SilverPink = Color(0xFFDEC3BE)
val KombuGreen = Color(0xFF3A4F41)

val LightGray = Color(0xFFadb5bd)
val MediumGray = Color(0xFF6c757d)
val DarkGray = Color(0xFF343a40)

val HighRate = Color(0xFF008000)
val MediumRate = Color(0xFFeeef20)
val LowRate = Color(0xFFd00000)

val DarkCard = Color(0xFF212529)
val LightCard = Color(0xFFe9ecef)


val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val ShimmerDarkGrayVariant = Color(0xFF252525)
val ShimmerMediumGrayVariant = Color(0xFFACACAC)

val Colors.tabIndicatorColor
	@Composable
	get() = if (isLight) BrightMaroon else LightGray

val Colors.backGroundColor
	@Composable
	get() = if (isLight) BackGroundLight else BackGroundDark

val Colors.topBarContentColor
	@Composable
	get() = if (isLight) Color.Black else LightGray

val Colors.topBarBgColor
	@Composable
	get() = if (isLight) Color.White else Color.Black

val Colors.cardColor
	@Composable
	get() = if (isLight) LightCard else DarkCard

val Colors.cardContentColor
	@Composable
	get() = if (isLight) DarkGray else LightGray

val Colors.contentColor
	@Composable
	get() = if (isLight) Color.Black else Color.White

val Colors.pagerIndicatorActiveColor
	@Composable
	get() = if (isLight) BrightMaroon else DarkGray

val Colors.cardShimmerColor
	@Composable
	get() = if (isLight) ShimmerMediumGray else ShimmerDarkGray

val Colors.ratingShimmerColor
	@Composable
	get() = if (isLight) ShimmerMediumGrayVariant else ShimmerDarkGrayVariant
