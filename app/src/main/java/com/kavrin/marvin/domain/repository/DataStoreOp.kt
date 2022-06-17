package com.kavrin.marvin.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOp {

	suspend fun saveOnBoardingState(completed: Boolean)

	fun readOnBoardingState(): Flow<Boolean>
}