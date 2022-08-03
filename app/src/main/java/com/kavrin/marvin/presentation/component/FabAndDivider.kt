package com.kavrin.marvin.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import com.kavrin.marvin.R
import com.kavrin.marvin.navigation.DurationConstants
import com.kavrin.marvin.ui.theme.*
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun FabAndDivider(
    collapsingToolbarState: CollapsingToolbarScaffoldState,
    fabState: MutableTransitionState<FabState>,
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit
) {

    ///// FAB Animations /////
    SideEffect {
        fabState.targetState = FabState.End
    }
    val transition =
        updateTransition(targetState = fabState, label = stringResource(R.string.fab_animation))

    val animFabTranslateX by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = DurationConstants.MEDIUM,
                delayMillis = DurationConstants.SHORT,
                easing = FastOutSlowInEasing
            )
        }, label = stringResource(R.string.fab_translate_x)
    ) { state ->
        when (state.targetState) {
            FabState.Start -> 300f
            FabState.End -> 0f
        }
    }

    val animRotate by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = DurationConstants.MEDIUM,
                delayMillis = DurationConstants.SHORT,
                easing = FastOutSlowInEasing
            )
        },
        label = stringResource(R.string.fab_rotate)
    ) { state ->
        when (state.targetState) {
            FabState.Start -> 360f
            FabState.End -> 0f
        }
    }

    Divider(
        modifier = Modifier
            .offset {
                IntOffset(x = 0, y = collapsingToolbarState.toolbarState.height)
            }
            .graphicsLayer {
                alpha = 1f - collapsingToolbarState.toolbarState.progress
            },
        thickness = XX_SMALL_PADDING,
    )

    FloatingActionButton(
        modifier = modifier
            .padding(horizontal = SMALL_PADDING)
            .size(FAB_SIZE)
            .offset {
                IntOffset(
                    x = 0,
                    y = (collapsingToolbarState.toolbarState.height.toDp() - FAB_OFFSET).roundToPx()
                )
            }
            .graphicsLayer {
                translationX = animFabTranslateX
                rotationZ = animRotate
            },
        onClick = onFabClicked,
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