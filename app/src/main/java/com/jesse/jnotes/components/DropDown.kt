package com.jesse.jnotes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
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
            Icon(Icons.Default.ArrowDropDown, "select")
            Text("Select")
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
private fun Preview() {
    val (state, setState) = remember { mutableStateOf("hi") }
    Column {
        Text(state)
        DropDownMenuComponent(
            content = listOf("a", "b", "c"),
            value = setState)
    }
}