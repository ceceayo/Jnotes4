package com.jesse.jnotes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.*
import jp.kaleidot725.texteditor.state.TextEditorState
import jp.kaleidot725.texteditor.view.TextEditor

@Composable
fun BlockEditor(
    blockValue: NoteBlock,
    note: Note,
    globalConfig: MutableState<ConfigData?>,
    selectedStorageApi: MutableState<StorageApi?>,
    currentNote: NoteContent
) {
    var textEditorState by remember { mutableStateOf(TextEditorState.create("aaa")) }
    Box(modifier = Modifier.fillMaxSize()) {
        TextEditor(
            textEditorState = textEditorState,
            onChanged = {
                textEditorState = it;
                var newNote = currentNote.copy {
                    blocks[blockValue.id] = blocks[blockValue.id].copy {
                        content = it.getAllText()
                    }
                }
                val f = selectedStorageApi.value!!.setBinFileContents(
                    arrayOf("notes").plus(note.pathList),
                    "content",
                )
                newNote.writeTo(f.outputStream())
            },
        )
    }
    }