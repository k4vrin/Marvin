package com.kavrin.marvin.presentation.screens.detail.state.toolbar.scroll_flags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import com.kavrin.marvin.presentation.screens.detail.state.toolbar.ScrollFlagState

class ScrollState(
    heightRange: IntRange,
    scrollOffset: Float = 0f
) : ScrollFlagState(heightRange) {


    override var _scrollOffset: Float by mutableStateOf(
        value = scrollOffset.coerceIn(0f, maxHeight.toFloat()),
        policy = structuralEqualityPolicy()
    )

    // If the top of the list has been reached,
    // _scrollOffset will be updated with a value between 0 and maxHeight.
    // Otherwise, _scrollOffset will remain the same. Also, _consumed will be updated accordingly.
    override var scrollOffset: Float
        get() = _scrollOffset
        set(value) {
            if (scrollTopLimitReached) {
                val oldOffset = _scrollOffset
                _scrollOffset = value.coerceIn(0f, maxHeight.toFloat())
                _consumed = oldOffset - _scrollOffset
            } else {
                _consumed = 0f
            }
        }

    // offset takes a value between 0 and -minHeight. When scrollOffset is ≤ rangeDifference,
    // offset is 0, which means that the toolbar is completely visible. When scrollOffset is > rangeDifference,
    // offset takes a negative value, so the toolbar is partially or totally off-screen.
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
                    ScrollState(
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
//    return rememberSaveable(saver = ScrollState.Saver) {
//        ScrollState(toolbarHeightRange)
//    }
//}
