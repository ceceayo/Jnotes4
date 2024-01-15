package com.jesse.jnotes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun DropDownMenuComponent(
    content: List<String>,
    value: (String) -> Unit
) {
    Column {
        var expanded by remember { mutableStateOf(false) }
        Button(onClick = { expanded = !expanded }) {
            Text("test")
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            Modifier
                .fillMaxWidth()
                .padding(
                    Dp(16F)
                )
        ) {
            content.forEach() {
                DropdownMenuItem(onClick = {
                    expanded = false
                    value( it )
                }, text = {
                    Text(it)
                })
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    val (state, setState) = remember { mutableStateOf("hi") }
    Column {
        Text(state)
        DropDownMenuComponent(
            content = listOf("a", "b", "c"),
            value = setState)
    }
}