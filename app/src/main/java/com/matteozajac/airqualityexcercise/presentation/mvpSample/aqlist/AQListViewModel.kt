package com.matteozajac.airqualityexcercise.presentation.mvpSample.aqlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.LoadStationsUseCase

class AQListViewModel : ViewModel() {
    private val loadStationsUseCase = LoadStationsUseCase()
    private val stations: MutableLiveData<List<AQStation>> by lazy {

        MutableLiveData<List<AQStation>>().also {
            it.value = loadStationsUseCase.execute()
        }
    }

    fun getStations(): LiveData<List<AQStation>> {
        return stations
    }

    fun onLoadButtonClicked() {

    }
}