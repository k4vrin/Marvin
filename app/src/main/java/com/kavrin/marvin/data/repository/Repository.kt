package com.kavrin.marvin.data.repository

import com.kavrin.marvin.domain.repository.DataStoreOp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
	private val dataStore: DataStoreOp
) {

	suspend fun saveOnBoardingState(completed: Boolean) {
		dataStore.saveOnBoardingState(completed = completed)
	}

	fun readOnBoardingState(): Flow<Boolean> {
		return dataStore.readOnBoardingState()
	}
}