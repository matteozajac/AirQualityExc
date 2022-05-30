package com.matteozajac.airqualityexcercise.presentation.aqList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.LoadStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AQListViewModel @Inject constructor(private val loadStationsUseCase: LoadStationsUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<UIState<List<AQStation>>>(UIState.Initial)
    val state: StateFlow<UIState<List<AQStation>>>
        get() = _state

    init {
        loadStations()

    }

    private fun loadStations() = viewModelScope.launch {
        try {


            _state.value = UIState.Loading
            val stations = withContext(Dispatchers.IO) {

                loadStationsUseCase.execute()
            }
            _state.value = UIState.Success(stations)
        } catch (e: Exception) {
            _state.value = UIState.Failure("Wystapil blad")

        }
    }

    sealed class UIState<out T : Any> {
        object Initial : UIState<Nothing>()
        object Loading : UIState<Nothing>()

        data class Success<out T : Any>(val value: T) : UIState<T>()
        data class Failure(val message: String) : UIState<Nothing>()
    }
}