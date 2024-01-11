package com.jesse.jnotes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun DropDownMenuComponent(
    content: List<String>,
    value: MutableState<String>
) {
    Column {
        var expanded by remember { mutableStateOf(false) }
        Button(onClick = {expanded = !expanded}){
            Text("test")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, Modifier.fillMaxWidth().padding(
            Dp(16F)
        )) {
            content.forEach() {
                Text(it)
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    DropDownMenuComponent(content = listOf("a", "b", "c"), value = remember { mutableStateOf("hi") })
}