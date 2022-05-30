package com.matteozajac.airqualityexcercise.ui.mvpSample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.matteozajac.airqualityexcercise.R
import com.matteozajac.airqualityexcercise.presentation.mvpSample.MvpAbstractView
import com.matteozajac.airqualityexcercise.presentation.mvpSample.MvpPresenter

//Concrete view
class MVPActivity : AppCompatActivity(), MvpAbstractView {
    var textView: TextView? = null

    val presenter = MvpPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpactivity)

        textView = findViewById(R.id.initial_text_view)

        findViewById<Button>(R.id.load_button).setOnClickListener {
            presenter.onLoadButtonClicked()
        }
    }

    override fun displayText(text: String) {
        textView?.text = text
    }
}