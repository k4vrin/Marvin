package com.kavrin.marvin.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val BackGround = Color(0xFFF5F5F5)

val BrightMaroon = Color(0xFFB9314F)
val AntiqueBrass = Color(0xFFD5A18E)
val SilverPink = Color(0xFFDEC3BE)
val KombuGreen = Color(0xFF3A4F41)

val LightGray = Color(0xFFadb5bd)
val MediumGray = Color(0xFF6c757d)
val DarkGray = Color(0xFF343a40)

val Colors.backGroundColor
	@Composable
	get() = if (isLight) BackGround else Color.Black