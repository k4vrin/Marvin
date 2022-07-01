package com.kavrin.marvin.presentation.screens.detail.state.toolbar

abstract class ScrollFlagState(heightRange: IntRange) : ToolbarState {

    init {
        require(heightRange.first >= 0 && heightRange.last >= heightRange.first) {
            "The lowest height value must be >= 0 and the highest height value must be >= the lowest value."
        }
    }

    protected val minHeight = heightRange.first
    protected val maxHeight = heightRange.last
    protected val rangeDifference = maxHeight - minHeight
    protected var _consumed: Float = 0f

    protected abstract var _scrollOffset: Float

    // height takes a value between minHeight and maxHeight. When scrollOffset is ≥ rangeDifference,
    // the toolbar is collapsed. When scrollOffset is 0, the toolbar is expanded.
    final override val height: Float
        get() = (maxHeight - scrollOffset).coerceIn(
            minimumValue = minHeight.toFloat(),
            maximumValue = maxHeight.toFloat()
        )

    final override val progress: Float
        get() = 1 - (maxHeight - height) / rangeDifference

    final override val consumed: Float
        get() = _consumed

    final override var scrollTopLimitReached: Boolean = true
}