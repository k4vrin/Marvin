package com.kavrin.marvin.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.use_cases.auth.AuthUseCases
import com.kavrin.marvin.presentation.screens.auth.ValidationEvent
import com.kavrin.marvin.util.Resource
import com.kavrin.marvin.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    private val _state: MutableStateFlow<RegisterStates> = MutableStateFlow(RegisterStates())
    val state = _state.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UsernameChanged -> {
                _state.value = _state.value.copy(username = event.username)
            }
            is RegisterEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is RegisterEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is RegisterEvent.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {
        val emailResult = useCases.validateEmail(email = state.value.email)
        val usernameResult = useCases.validateUsername(username = state.value.username)
        val passwordResult = useCases.validatePassword(password = state.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
            usernameResult
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                usernameError = usernameResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            useCases.userSignup(
                email = state.value.email,
                username = state.value.username,
                password = state.value.password
            ).onEach { res ->
                when (res) {
                    is Resource.Success -> validationEventChannel.send(ValidationEvent.Success)
                    is Resource.Error -> validationEventChannel.send(
                        ValidationEvent.Error(
                            res.message ?: UiText.DynamicString("Unknown error")
                        )
                    )
                    is Resource.Loading -> _state.value = state.value.copy(isRegisterBtnDisabled = true)
                }
            }
        }
    }

}