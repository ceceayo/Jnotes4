package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.views.destinations.ConfigNewVaultPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun ConfigPage(
    navigator: DestinationsNavigator
) {
    Scaffold() {
        Column() {
            Text("JNotes")
            Button(onClick = {
                navigator.navigate(ConfigNewVaultPageDestination(ConfigNewVaultPageDestination.NavArgs(null)))

            }, Modifier
                .fillMaxWidth()
                .padding(Dp(16F))
            ) {
                Text("Create a new vault")
            }
        }
    }
}