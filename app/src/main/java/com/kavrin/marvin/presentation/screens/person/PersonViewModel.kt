package com.kavrin.marvin.presentation.screens.person

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavrin.marvin.domain.model.person.PersonMovieCast
import com.kavrin.marvin.domain.model.person.PersonMovieCrew
import com.kavrin.marvin.domain.model.person.PersonTvCast
import com.kavrin.marvin.domain.model.person.PersonTvCrew
import com.kavrin.marvin.domain.use_cases.person.PersonUseCases
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarState
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val useCases: PersonUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>(Constants.ARGUMENT_KEY_ID)

    private val _collapsingToolbar = mutableStateOf(
        CollapsingToolbarScaffoldState(
            CollapsingToolbarState()
        )
    )
    val collapsingToolbar: State<CollapsingToolbarScaffoldState> = _collapsingToolbar

    private val _personRes = MutableStateFlow<NetworkResult>(NetworkResult.Loading())
    val personRes: StateFlow<NetworkResult> = _personRes

    private val _personToolbar = MutableStateFlow<Map<String, String?>>(emptyMap())
    val personToolbar: StateFlow<Map<String, String?>> = _personToolbar

    private val _personInfo = MutableStateFlow<Map<String, Int?>>(emptyMap())
    val personInfo: StateFlow<Map<String, Int?>> = _personInfo

    private val _personBio = MutableStateFlow<String?>(null)
    val personBio: StateFlow<String?> = _personBio

    private val _personMovieCast = MutableStateFlow<List<PersonMovieCast>?>(null)
    val personMovieCast: StateFlow<List<PersonMovieCast>?> = _personMovieCast

    private val _personMovieCrew = MutableStateFlow<List<PersonMovieCrew>?>(null)
    val personMovieCrew: StateFlow<List<PersonMovieCrew>?> = _personMovieCrew

    private val _personTvCast = MutableStateFlow<List<PersonTvCast>?>(null)
    val personTvCast: StateFlow<List<PersonTvCast>?> = _personTvCast

    private val _personTvCrew = MutableStateFlow<List<PersonTvCrew>?>(null)
    val personTvCrew: StateFlow<List<PersonTvCrew>?> = _personTvCrew


    fun getDetails() {
        _personRes.value = NetworkResult.Loading()
        viewModelScope.launch(context = Dispatchers.IO) {
            id?.let {
                _personRes.value = useCases.getPersonDetails(id = it)
                _personToolbar.value = useCases.getPersonDetails.getToolbarDetails()
                _personInfo.value = useCases.getPersonDetails.getInfoDetails()
                _personBio.value = useCases.getPersonDetails.getBio()
                _personMovieCast.value = useCases.getPersonDetails.getCastMovies()
                _personMovieCrew.value = useCases.getPersonDetails.getCrewMovies()
                _personTvCast.value = useCases.getPersonDetails.getCastTvs()
                _personTvCrew.value = useCases.getPersonDetails.getCrewTvs()
            }
        }
    }

}