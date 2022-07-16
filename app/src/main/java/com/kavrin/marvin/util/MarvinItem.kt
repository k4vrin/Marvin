package com.kavrin.marvin.util

import androidx.compose.runtime.Stable
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv

@Stable
interface MarvinItem {
	val movie: Movie? get() = null
	val tv: Tv? get() = null
}