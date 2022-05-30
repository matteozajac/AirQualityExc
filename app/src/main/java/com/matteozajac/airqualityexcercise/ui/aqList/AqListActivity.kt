package com.matteozajac.airqualityexcercise.ui.aqList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.presentation.aqList.AQListViewModel
import com.matteozajac.airqualityexcercise.ui.theme.AirQualityExcerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class AqListActivity : ComponentActivity() {

    val viewmodel: AQListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollableState = rememberScrollState()

            AirQualityExcerciseTheme {
                Scaffold(
                    Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) { padding ->
                    Column(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .verticalScroll(
                                state = scrollableState
                            )) {

                        Spacer(Modifier.height(120.dp))

                        viewmodel.getStations().value?.forEach { station ->
                            AQListItem(station)
                        }

                        Text("This is the end...")
                    }
                }
            }
        }
    }

    @Composable
    fun AQListItem(station: AQStation) {
        Text(
            text = station.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        )
    }
}