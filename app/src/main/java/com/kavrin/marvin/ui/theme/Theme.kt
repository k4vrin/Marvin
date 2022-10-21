package com.kavrin.marvin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MarvinTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


val marvinTextFieldColors
    @Composable
    get() = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.topBarContentColor,
        focusedIndicatorColor = MaterialTheme.colors.fabBgColor,
        focusedLabelColor = MaterialTheme.colors.fabBgColor,
        unfocusedIndicatorColor = MaterialTheme.colors.fabBgColor.copy(alpha = ContentAlpha.disabled),
        unfocusedLabelColor = MaterialTheme.colors.topBarContentColor.copy(alpha = ContentAlpha.disabled),
        errorIndicatorColor = RedNCS,
        errorLabelColor = RedNCS,
        errorTrailingIconColor = RedNCS
    )

val marvinTextFieldTextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nunitoTypeFace,
        fontSize = MaterialTheme.typography.h6.fontSize,
        color = MaterialTheme.colors.cardContentColor
    )

val marvinTextFieldLabel: @Composable (content: String) -> Unit = { content ->
    Text(
        modifier = Modifier
            .alpha(ContentAlpha.medium),
        fontFamily = nunitoTypeFace,
        fontSize = MaterialTheme.typography.body1.fontSize,
        text = content,
    )
}

@Composable
fun PassTxtFieldIcon(
    showPassword: Boolean,
    onIconClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onIconClicked,
        ) {
            Icon(
                imageVector =
                if (showPassword) Icons.Rounded.Visibility
                else Icons.Rounded.VisibilityOff,
                contentDescription = "Password Icon",
            )
        }
    }
}