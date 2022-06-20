package com.kavrin.marvin.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.use_cases.splash_welcome.SplashWelcomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: SplashWelcomeUseCases
) : ViewModel() {

	fun saveOnBoardingState(completed: Boolean) {
		viewModelScope.launch(context = Dispatchers.IO) {
			useCases.saveOnBoarding(completed = completed)
		}
	}
}