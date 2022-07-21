package com.kavrin.marvin.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val BackGroundLight = Color(0xFFF0F0F0)
val BackGroundDark = Color(0xFF090909)

val BrightMaroon = Color(0xFFB9314F)
val AntiqueBrass = Color(0xFFD5A18E)
val SilverPink = Color(0xFFDEC3BE)
val KombuGreen = Color(0xFF3A4F41)


val PlumpPurple = Color(0xFF6247AA)
val Amethyst = Color(0xFF9163CB)
val LavenderFloral = Color(0xFFB185DB)
val Thistle = Color(0xFFDEC9E9)

val LightGray = Color(0xFFadb5bd)
val MediumGray = Color(0xFF6c757d)
val DarkGray = Color(0xFF151418)

val HighRate = Color(0xFF008000)
val MediumRate = Color(0xFFeeef20)
val LowRate = Color(0xFFd00000)

val DarkCard = Color(0xFF212529)
val LightCard = Color(0xFFDEC9E9)


val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val ShimmerDarkGrayVariant = Color(0xFF252525)
val ShimmerMediumGrayVariant = Color(0xFFACACAC)

val LightFab = Color(0xFF9163CB)

val GreenRYB = Color(0xFF55A630)
val AppleGreen = Color(0xFF80B918)

val RedNCS = Color(0xFFB21E35)
val RoseMadder = Color(0xFFE01E37)

val CyberYellow = Color(0xFFFFD400)
val MinionYellow = Color(0xFFFFE566)

val EerieBlack = Color(0xFF212529)
val DavysGrey = Color(0xFF495057)

val RatingGreen = Brush.horizontalGradient(
    listOf(AppleGreen, GreenRYB)
)

val RatingYellow = Brush.horizontalGradient(
    listOf(MinionYellow, CyberYellow)
)

val RatingRed = Brush.horizontalGradient(
    listOf(RoseMadder, RedNCS)
)


val Colors.tabIndicatorColor
    @Composable
    get() = if (isLight) LightFab else LightGray

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
    get() = if (isLight) LightFab else LightGray

val Colors.cardShimmerColor
    @Composable
    get() = if (isLight) ShimmerMediumGray else ShimmerDarkGray

val Colors.ratingShimmerColor
    @Composable
    get() = if (isLight) ShimmerMediumGrayVariant else ShimmerDarkGrayVariant

val Colors.fabBgColor
    @Composable
    get() = if (isLight) LightFab else LightGray

val Colors.fabContentColor
    @Composable
    get() = if (isLight) Color.White else Color.Black

val Colors.statusBarColor
    @Composable
    get() = if (isLight) Color.White else Color.Black

val Colors.loadingCircleColor
    @Composable
    get() = if (isLight) PlumpPurple else Color.White

val Colors.splashText
    @Composable
    get() = if (isLight) PlumpPurple else Color.White

val Colors.bottomNavigationBackground
    @Composable
    get() = if (isLight) LightFab else Color.Black

val Colors.bottomNavigationContent
    @Composable
    get() = if (isLight) Color.Black else Color.White

val Colors.splashBackgroundBrush
    @Composable
    get() = if (isLight)
        Brush.verticalGradient(listOf(Thistle, LavenderFloral))
    else
        Brush.verticalGradient(listOf(BackGroundDark, BackGroundDark))


val Colors.ratingBackground
    @Composable
    get() = if (isLight)
        Brush.horizontalGradient(listOf(DavysGrey, EerieBlack))
    else
        Brush.horizontalGradient(listOf(Color.White, ShimmerLightGray))

val Colors.cardGradientColor
    @Composable
    get() = if (isLight)
        Brush.linearGradient(
            listOf(LightCard, LavenderFloral),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    else
        Brush.linearGradient(
            listOf(DarkCard, ShimmerDarkGray),
            start = Offset.Zero,
            end = Offset.Infinite
        )
