package com.kavrin.marvin.presentation.screens.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.use_cases.splash_welcome.SplashWelcomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val useCases: SplashWelcomeUseCases
) : ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            _onBoardingCompleted.value =
                useCases.readOnBoarding().stateIn(scope = viewModelScope).value
        }
    }

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(context = Dispatchers.IO) {
            useCases.saveOnBoarding(completed = completed)
        }
    }

}