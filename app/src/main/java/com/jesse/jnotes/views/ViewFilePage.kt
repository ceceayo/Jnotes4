package com.jesse.jnotes.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigData
import com.ramcosta.composedestinations.annotation.Destination
import jp.kaleidot725.texteditor.state.TextEditorState
import jp.kaleidot725.texteditor.view.TextEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ViewFilePage(
    file: Int, config: MutableState<ConfigData?>, selectedStorageApi: MutableState<StorageApi?>
) {
    val note = config.value!!.notesList[file]
    Scaffold(/*topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(config.value!!.notesList[file].name)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }*/) {
        var text = selectedStorageApi.value!!.getFileContents(arrayOf("notes").plus(note.pathList), "content")
        if (text == null) { text = "" }
        var textEditorState by remember { mutableStateOf(TextEditorState.create(text)) }
        Surface(Modifier.padding(Dp(8F)).background(Color.Black)) {
            TextEditor(
                textEditorState = textEditorState,
                onChanged = { it2 ->
                        textEditorState = it2
                        selectedStorageApi.value!!.setFileContents(arrayOf("notes").plus(note.pathList), "content", it2.getAllText())
                    },
                contentPaddingValues = it
            )
        }
    }
}