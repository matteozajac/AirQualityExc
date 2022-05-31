package com.matteozajac.airqualityexcercise.presentation.aqList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.LoadStationsException
import com.matteozajac.airqualityexcercise.logic.LoadStationsUseCase
import com.matteozajac.airqualityexcercise.presentation.common.UIState
import com.matteozajac.airqualityexcercise.presentation.errors.LoadStationsErrorHandler
import com.matteozajac.airqualityexcercise.presentation.errors.PresentationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AQListViewModel @Inject constructor(private val loadStationsUseCase: LoadStationsUseCase) :
    ViewModel() {

    private val loadStationsErrorHandler = LoadStationsErrorHandler()

    private val _state = MutableStateFlow<UIState<List<AQStation>>>(UIState.Initial)
    val state: StateFlow<UIState<List<AQStation>>>
        get() = _state

    init {
        loadStations()
    }

    fun loadStations() = viewModelScope.launch {
        try {
            _state.value = UIState.Loading
            val stations = withContext(Dispatchers.IO) {

                loadStationsUseCase.execute()
            }
            _state.value = UIState.Success(stations)
        } catch (e: LoadStationsException) {
            val presentationException = loadStationsErrorHandler.toPresentationError(e)
            _state.value = UIState.Failure(presentationException)

        } catch (e: Exception) {
            _state.value = UIState.Failure(
                PresentationException("Generyczny Wystapil blad", message = null, imageURL = null)
            )
        }
    }
}