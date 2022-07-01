package com.kavrin.marvin.presentation.screens.detail.state.toolbar

import androidx.compose.runtime.Stable

@Stable
interface ToolbarState {
    val offset: Float
    val height: Float
    val progress: Float
    val consumed: Float
    var scrollTopLimitReached: Boolean
    var scrollOffset: Float
}

/**
 * We need an interface to manage the toolbar state, not only to encapsulate all the data involved,
 * as I just mentioned, but to establish a contract about the behavior that every scroll flag implementation must provide,
 * in order to make them interchangeable.
 */