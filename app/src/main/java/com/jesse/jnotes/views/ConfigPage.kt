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
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfigPage(
    nav: NavController
) {
    Scaffold() {
        Column() {
            Text("JNotes")
            Button(onClick = {
                nav.navigate("config/new_vault")
            }, Modifier
                .fillMaxWidth()
                .padding(Dp(16F))
            ) {
                Text("Create a new vault")
            }
        }
    }
}