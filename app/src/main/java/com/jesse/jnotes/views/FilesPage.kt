package com.jesse.jnotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.Note
import com.jesse.jnotes.views.destinations.NewFilePageDestination
import com.jesse.jnotes.views.destinations.ViewFilePageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private data class FilesPageNestFolder(
    val name: String, val folders: List<FilesPageNestFolder>, val files: List<FilesPageNestFile>
)

private data class FilesPageNestFile(
    val name: String, val note: Note?, val index: Int
)

@Composable
private fun FilesPageNestFolderComponent(filesPageNestFolder: FilesPageNestFolder, nav: DestinationsNavigator) {
    Column(
        Modifier
            .padding(Dp(8F), Dp(0F), Dp(0F), Dp(0F))
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    start = Offset(-10F, 0F),
                    end = Offset(-10F, this.size.height),
                    color = Color.Gray
                )
            }) {
            Column {
                var visible by remember { mutableStateOf(false) }
                Button(onClick = {
                    visible = !visible
                }, Modifier.fillMaxWidth()) {
                    Text(filesPageNestFolder.name + "/")
                }
                if (visible) {
                    filesPageNestFolder.folders.forEach { folder ->
                        FilesPageNestFolderComponent(filesPageNestFolder = folder, nav)
                    }
                    filesPageNestFolder.files.forEach { file ->
                        FilesPageNestFileComponent(file = file, nav = nav, index = file.index)
                    }
                }

        }
    }
}

@Composable
private fun FilesPageNestFileComponent(file: FilesPageNestFile, nav: DestinationsNavigator, index: Int) {
    Button({nav.navigate(ViewFilePageDestination(file = index))}, Modifier.fillMaxWidth()) {
        Text(file.name)
    }
}

@Destination
@Composable
fun FilesPage(config: MutableState<ConfigData?>, nav: DestinationsNavigator) {
    Scaffold (floatingActionButton = {
        FloatingActionButton(onClick = {
            nav.navigate(NewFilePageDestination)
        }) {
            Icon(Icons.Filled.Add, "Add a new note to vault.")
        }
    }) {
        Column(Modifier.padding(it)) {
            Text("test")
            config.value!!.notesList.forEachIndexed { index, note ->
                FilesPageNestFileComponent(file = FilesPageNestFile(note.name, note, index), nav = nav, index = index)
            }
        }
    }
}