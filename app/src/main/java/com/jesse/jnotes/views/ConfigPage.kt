package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun ConfigPage(
    navigator: DestinationsNavigator,
    config: MutableState<ConfigData?>
) {
    config.value = null
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
            Text("or", Modifier.fillMaxWidth().drawBehind {
                this.drawLine(Color.Gray, Offset(20f, this.center.y), Offset(this.size.width - 20f, this.center.y))
                this.drawLine(Color.White, Offset(this.center.x-50f, this.center.y), Offset(this.center.x+50f, this.center.y))
            }, textAlign = TextAlign.Center)
            Button(onClick = {
                navigator.navigate(ConfigLoadVaultPageDestination(ConfigLoadVaultPageDestination.NavArgs(null)))
            }, Modifier
                .fillMaxWidth()
                .padding(Dp(16F))
            ) {
                Text("Load a vault")
            }
        }
    }
}