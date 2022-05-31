package com.matteozajac.airqualityexcercise.ui.aqList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.presentation.aqList.AQListViewModel
import com.matteozajac.airqualityexcercise.presentation.common.UIState
import com.matteozajac.airqualityexcercise.presentation.errors.AlertDialogErrorDisplayer
import com.matteozajac.airqualityexcercise.presentation.errors.ErrorDisplayer
import com.matteozajac.airqualityexcercise.presentation.errors.PresentationException
import com.matteozajac.airqualityexcercise.ui.theme.AirQualityExcerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class AqListActivity : ComponentActivity() {

    val viewmodel: AQListViewModel by viewModels()
    lateinit var errorDisplayer: ErrorDisplayer

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorDisplayer = AlertDialogErrorDisplayer(context = this)
        setContent {
            AirQualityExcerciseTheme {
                Scaffold(
                    Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    AQListView(viewModel = viewmodel)
                }
            }
        }
    }

    @Composable
    fun AQListItem(station: AQStation) {
        Row(
            Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Column() {
                Text(
                    text = station.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Sponsored by ${station.sponsor.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AsyncImage(
                model = station.sponsor.logoURL,
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )

        }

    }

    @Composable
    fun AQListView(viewModel: AQListViewModel) {
        val scrollableState = rememberScrollState()
        val state by viewModel.state.collectAsState()
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    state = scrollableState
                )
        ) {

            Spacer(Modifier.height(120.dp))
            when (state) {
                UIState.Initial -> {
                    Text(text = "Initial")
                }
                UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UIState.Success -> {
                    val stations = (state as UIState.Success).value
                    stations.forEach { station ->
                        AQListItem(station)
                    }
                }
                is UIState.Failure -> {
                    val exception = (state as UIState.Failure).exception
                    errorDisplayer.display(exception)
                    ErrorView(exception)
                }
            }


            Text("This is the end...")
        }
    }

    @Composable
    fun ErrorView(exception: PresentationException) {
        Column() {
            Text(text = exception.title, style = MaterialTheme.typography.titleMedium)
            exception.message?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
        }
    }

    @Preview
    @Composable
    fun AQListScreenPreview() {
        AQListItem(
            station = AQStation(
                name = "Krakow",
                sponsor = AQSponsor(
                    name = "Fryderyk Muras",
                    logoURL = "https://googlechrome.github.io/samples/picture-element/images/kitten-small.png"
                )
            ),
        )
    }
}