package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.views.destinations.ConfigLoadVaultPageDestination
import com.jesse.jnotes.views.destinations.ConfigNewVaultPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun ConfigPage(
    navigator: DestinationsNavigator, config: MutableState<ConfigData?>
) {
    config.value = null
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("JNotes") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            Button(
                onClick = {
                    navigator.navigate(
                        ConfigNewVaultPageDestination(
                            ConfigNewVaultPageDestination.NavArgs(
                                null
                            )
                        )
                    )

                },
                Modifier
                    .fillMaxWidth()
                    .padding(Dp(16F))
            ) {
                Text("Create a new vault")
            }
            Text("or",
                Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        this.drawLine(
                            Color.Black,
                            Offset(20f, this.center.y),
                            Offset(this.center.x - 50f, this.center.y)
                        )
                        this.drawLine(
                            Color.Black,
                            Offset(this.center.x + 50f, this.center.y),
                            Offset(this.size.width - 20f, this.center.y)
                        )
                    }, textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    navigator.navigate(
                        ConfigLoadVaultPageDestination(
                            ConfigLoadVaultPageDestination.NavArgs(
                                null
                            )
                        )
                    )
                },
                Modifier
                    .fillMaxWidth()
                    .padding(Dp(16F))
            ) {
                Text("Load a vault")
            }
        }
    }
}