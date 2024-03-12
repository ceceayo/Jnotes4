package com.jesse.jnotes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.Note
import com.jesse.jnotes.proto.NoteBlock
import com.jesse.jnotes.proto.NoteContent
import com.jesse.jnotes.proto.copy
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
    var textEditorState by remember { mutableStateOf(TextEditorState.create(blockValue.content.toString())) }
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
                    arrayOf("notes").plus(note.pathList).plus(arrayOf(note.name)),
                    "content",
                )
                newNote.writeTo(f.outputStream())
            },
        )
    }
    }