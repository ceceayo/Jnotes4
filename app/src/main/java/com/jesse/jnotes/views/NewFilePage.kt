package com.jesse.jnotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.components.DropDownMenuComponent
import com.jesse.jnotes.proto.ConfigData
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination
fun NewFilePage(nav: DestinationsNavigator, config: MutableState<ConfigData?>) {
    var type by remember {mutableStateOf("-- please select a type --")}
    var folders = remember { mutableStateListOf<String>() }
    var filename by remember { mutableStateOf<String>("") }
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()) {
        Text("New Note", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Column(Modifier.padding(Dp(12F))) {
            Text("Type: $type")
            var types = listOf<String>()
            config.value!!.notetypesList.forEach {
                types += listOf(it.name)
            }
            DropDownMenuComponent(content = types) {
                type = it
            }
        }
        Column(Modifier.padding(Dp(12F))) {
            Text("Folder/File")
            folders.forEachIndexed { idx, obj ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(Dp(4F))) {
                    TextField(obj, {newValue: String ->
                        folders[idx] = newValue
                    }, Modifier.fillMaxWidth(0.8F))
                    IconButton(onClick = { folders.removeAt(idx) }) {
                        Icon(Icons.Filled.Delete, "Delete this folder from the list")
                    }
                }
            }
            Row() {
                Button({
                    folders.add("folder")
                }) {
                    Text("New folder")
                }
            }

            TextField(filename, { newName ->
                filename = newName
            }, Modifier.fillMaxWidth().padding(Dp(8f)))
            Button({
                //val notetype =
                //config.value!!.notesList.add(note {

                //})
            }, Modifier
                .fillMaxWidth()){
                Icon(Icons.Filled.Add, "Add Note")
                Text("Add Note")
            }
        }
    }
}