package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun FabAndDivider(
    collapsingToolbarState: CollapsingToolbarScaffoldState,
    animFabTranslateX: Float,
    animRotate: Float,
    modifier: Modifier = Modifier
) {

    Divider(
        modifier = Modifier
            .offset(
                y = with(LocalDensity.current) { collapsingToolbarState.toolbarState.height.toDp() }
            )
            .graphicsLayer {
                alpha = 1f - collapsingToolbarState.toolbarState.progress
            },
        thickness = 2.dp
    )

    FloatingActionButton(
        modifier = modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = (collapsingToolbarState.toolbarState.height.toDp() - FAB_OFFSET).roundToPx()
                )
            }
            .graphicsLayer {
                translationX = animFabTranslateX
                rotationZ = animRotate
            }
            .padding(horizontal = SMALL_PADDING)
            .size(FAB_SIZE),
        onClick = { },
        backgroundColor = MaterialTheme.colors.fabBgColor,
        contentColor = Color.White
    ) {

        Icon(
            modifier = Modifier
                .fillMaxSize()
                .requiredSize(ICON_SIZE),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_icon),
            tint = MaterialTheme.colors.fabContentColor
        )
    }
}

enum class FabState {
    Start,
    End
}