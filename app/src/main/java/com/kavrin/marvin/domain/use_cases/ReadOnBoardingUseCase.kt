package com.kavrin.marvin.domain.use_cases

import com.kavrin.marvin.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<Boolean> {
		return repository.readOnBoardingState()
	}
}