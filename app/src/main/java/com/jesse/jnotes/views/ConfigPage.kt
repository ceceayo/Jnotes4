package com.jesse.jnotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.jesse.jnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigPage(nav: NavController) {
    val scrollState = rememberScrollState()
    val storageApiSelectorDropdownEnabled = false
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Configuration For JNotes",
            modifier = Modifier.fillMaxWidth()
            )
        Text("Pre Launch Configuration for JNotes", Modifier.align(Alignment.CenterHorizontally))



        Button(onClick = {
                         nav.navigate("home")
        },
            Modifier
                .fillMaxWidth()
                .padding(Dp(24F))
            ) {
            Text("Go!")
        }
    }
}