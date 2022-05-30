package com.matteozajac.airqualityexcercise.logic

import org.joda.time.DateTime

//Model
class LoadDateUseCase {
    fun execute(): DateTime {
        return DateTime.now()
    }
}