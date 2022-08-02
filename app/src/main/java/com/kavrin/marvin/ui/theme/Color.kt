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

val BackGroundLight = Color(0xFFEDF6F9)
val BackGroundDark = Color(0xFF090909)

val BrightMaroon = Color(0xFFB9314F)


val Ming = Color(0xFF006D77)
val MingLighter = Color(0xFF66a7ad)
val MingDarker = Color(0xFF004147)
val MiddleBlueGreen = Color(0xFF83C5BE)
val MingLightCard = Color(0xFF99c5c9)
val MingDarkCard = Color(0xFF002c30)
val MingLightBackground = Color(0xFFcce2e4)
val MingDarkBackground = Color(0xFF001618)
val MingLightBarColor = Color(0xFFf6fbfc)

val LightGray = Color(0xFFadb5bd)
val MediumGray = Color(0xFF6c757d)
val DarkGray = Color(0xFF151418)

val HighRate = Color(0xFF008000)
val MediumRate = Color(0xFFeeef20)
val LowRate = Color(0xFFd00000)


val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFACACAC)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val ShimmerDarkGrayVariant = Color(0xFF252525)

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
    get() = if (isLight) Ming else MingLighter

val Colors.backGroundColor
    @Composable
    get() = if (isLight) BackGroundLight else BackGroundDark

val Colors.topBarContentColor
    @Composable
    get() = if (isLight) Color.Black else LightGray

val Colors.topBarBgColor
    @Composable
    get() = if (isLight) MingLightBarColor else Color.Black

val Colors.secondaryCardColor
    @Composable
    get() = if (isLight) MingLightCard else MingDarkCard

val Colors.primaryCardColor
    @Composable
    get() = if (isLight) MingLightBackground else MingDarkBackground

val Colors.cardContentColor
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.contentColor
    @Composable
    get() = if (isLight) Color.Black else Color.White

val Colors.pagerIndicatorActiveColor
    @Composable
    get() = if (isLight) Ming else MingLighter

val Colors.cardShimmerColor
    @Composable
    get() = if (isLight) ShimmerMediumGray else ShimmerDarkGray

val Colors.ratingShimmerColor
    @Composable
    get() = if (isLight) ShimmerLightGray else ShimmerDarkGrayVariant

val Colors.fabBgColor
    @Composable
    get() = if (isLight) Ming else MingLighter

val Colors.fabContentColor
    @Composable
    get() = if (isLight) Color.White else Color.Black

val Colors.statusBarColor
    @Composable
    get() = topBarBgColor

val Colors.loadingCircleColor
    @Composable
    get() = if (isLight) MingLighter else Color.White

val Colors.splashText
    @Composable
    get() = if (isLight) Color.White else Color.White

val Colors.bottomNavigationBackground
    @Composable
    get() = if (isLight) MingLightBarColor else Color.Black

val Colors.bottomNavigationContent
    @Composable
    get() = if (isLight) Color.Black else Color.White

val Colors.splashBackgroundBrush
    @Composable
    get() = if (isLight)
        Brush.verticalGradient(listOf(MingLighter, MingDarker))
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
            listOf(MingLighter, MingLightCard),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    else
        Brush.linearGradient(
            listOf(MingDarkCard, MingDarker),
            start = Offset.Zero,
            end = Offset.Infinite
        )
