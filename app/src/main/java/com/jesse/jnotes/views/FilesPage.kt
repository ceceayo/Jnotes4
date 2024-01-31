package com.jesse.jnotes.views

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.Note

private data class FilesPageNestFolder(
    val name: String, val folders: List<FilesPageNestFolder>, val files: List<FilesPageNestFile>
)

private data class FilesPageNestFile(
    val name: String, val note: Note?
)

@Composable
private fun FilesPageNestFolderComponent(filesPageNestFolder: FilesPageNestFolder) {
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
                        FilesPageNestFolderComponent(filesPageNestFolder = folder)
                    }
                    filesPageNestFolder.files.forEach { file ->
                        Text(file.name)
                    }
                }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesPage(config: MutableState<ConfigData?>) {
    Scaffold {
        Column(Modifier.padding(it)) {
            Text("aaaa")
            config.value!!.notesList.forEach { note ->
                Text(note.name)
            }
        }
    }
}

@Preview
@Composable
private fun FilesPageNestFolderComponentPreview() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState).padding(Dp(8F))) {
        FilesPageNestFolderComponent(
            filesPageNestFolder = FilesPageNestFolder(
                files = emptyList(), folders = listOf(
                    FilesPageNestFolder(
                        files = listOf(FilesPageNestFile(name = "test", note = null)),
                        folders = emptyList(),
                        name = "bbb"
                    )
                ), name = "aaa"
            )
        )
    }
}