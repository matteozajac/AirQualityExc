package com.matteozajac.airqualityexcercise.ui.aqList

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.matteozajac.airqualityexcercise.R
import com.matteozajac.airqualityexcercise.presentation.mvpSample.aqlist.AQListViewModel

class AqListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpactivity)

        val viewmodel: AQListViewModel by viewModels()
        viewmodel.getStations().observe(this) { stations ->
            findViewById<TextView>(R.id.initial_text_view).text = stations.count().toString()
        }
    }
}