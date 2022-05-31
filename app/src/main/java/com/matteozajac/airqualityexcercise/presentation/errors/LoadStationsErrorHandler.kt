package com.matteozajac.airqualityexcercise.presentation.errors

import android.app.AlertDialog
import android.content.Context
import com.matteozajac.airqualityexcercise.logic.LoadStationsException

class LoadStationsErrorHandler {
    fun toPresentationError(loadStationsException: LoadStationsException): PresentationException {
        return PresentationException(
            title = "We're having some issues",
            message = "Please forgive us",
            imageURL = null
        )
    }
}

data class PresentationException(
    val title: String,
    val message: String?,
    val imageURL: String?
)

interface ErrorDisplayer {
    fun display(presentationException: PresentationException)
}

class AlertDialogErrorDisplayer(private val context: Context) : ErrorDisplayer {
    override fun display(presentationException: PresentationException) {

        val builder: AlertDialog.Builder = context.let {
            AlertDialog.Builder(it)
        }

        builder.setTitle(presentationException.title)?.setMessage(presentationException.message)
            ?.create()?.show()
    }
}
