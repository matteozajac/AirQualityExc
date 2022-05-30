package com.matteozajac.airqualityexcercise.presentation.mvpSample

import com.matteozajac.airqualityexcercise.logic.LoadDateUseCase
import org.joda.time.format.DateTimeFormat

//Presenter
class MvpPresenter(val view: MvpAbstractView) {
    private val loadDateUseCase = LoadDateUseCase()

    fun onLoadButtonClicked() {
        val dateTime = loadDateUseCase.execute()
        view.displayText(DateTimeFormat.forPattern("d-MMMM-YYYY hh:mm:ss").print(dateTime))
    }
}