package com.kavrin.marvin.presentation.screens.detail.state.toolbar

abstract class FixedScrollFlagState(heightRange: IntRange) : ScrollFlagState(heightRange) {

    final override val offset: Float = 0f

}