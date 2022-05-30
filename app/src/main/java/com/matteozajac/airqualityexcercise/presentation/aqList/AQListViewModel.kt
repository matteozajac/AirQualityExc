package com.matteozajac.airqualityexcercise.presentation.aqList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.LoadStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AQListViewModel @Inject constructor(private val loadStationsUseCase: LoadStationsUseCase) : ViewModel() {
    private val stations: MutableLiveData<List<AQStation>> by lazy {
        MutableLiveData<List<AQStation>>().also {
            it.value = loadStationsUseCase.execute()
        }
    }

    fun getStations(): LiveData<List<AQStation>> {
        return stations
    }
}