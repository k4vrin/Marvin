package com.kavrin.marvin.presentation.screens.detail.state.toolbar.scroll_flags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import com.kavrin.marvin.presentation.screens.detail.state.toolbar.ScrollFlagState

class EnterAlwaysState(
    heightRange: IntRange,
    scrollOffset: Float = 0f
) : ScrollFlagState(heightRange) {

    override var _scrollOffset by mutableStateOf(
        value = scrollOffset.coerceIn(0f, maxHeight.toFloat()),
        policy = structuralEqualityPolicy()
    )

    override var scrollOffset: Float
        get() = _scrollOffset
        set(value) {
            val oldOffset = _scrollOffset
            _scrollOffset = value.coerceIn(0f, maxHeight.toFloat())
            _consumed = oldOffset - _scrollOffset
        }

    override val offset: Float
        get() = -(scrollOffset - rangeDifference).coerceIn(0f, minHeight.toFloat())


    companion object {
        val Saver = run {

            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollOffsetKey = "ScrollOffset"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollOffsetKey to it.scrollOffset
                    )
                },
                restore = {
                    EnterAlwaysState(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollOffset = it[scrollOffsetKey] as Float,
                    )
                }
            )
        }
    }

}


//@Composable
//private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
//    return rememberSaveable(saver = EnterAlwaysState.Saver) {
//        EnterAlwaysState(toolbarHeightRange)
//    }
//}