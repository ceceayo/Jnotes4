package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.google.protobuf.ByteString
import com.jesse.jnotes.components.ViewBlock
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.logic.blockPlugins
import com.jesse.jnotes.plugins.blocks.TextRenderBlock
import com.jesse.jnotes.proto.*
import com.ramcosta.composedestinations.annotation.Destination
import java.nio.charset.Charset

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ViewFilePage(
    file: Int, config: MutableState<ConfigData?>, selectedStorageApi: MutableState<StorageApi?>
) {
    val note = config.value!!.notesList[file]
    val note_type_string = config.value!!.notesList[file].noteType
    var noteType: NoteType? = null
    config.value!!.notetypesList.forEach { iter_notetype ->
        if (iter_notetype.name == note_type_string) {
            noteType = iter_notetype
        }
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(config.value!!.notesList[file].name.capitalize(Locale.current))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }) {
        val text = selectedStorageApi.value!!.getFileContents(
            arrayOf("notes").plus(note.pathList),
            "content"
        )
        val currentNote: NoteContent?
        if ((text == null) or (text == "") or (text == "null")) {
            currentNote = noteContent {
                title = note.name
                type = "test"
                blocks += noteBlock {
                    id = 0
                    content = "aaa"
                    rendered = "<h1>aaa</h1>"
                    state = NoteBlock.block_state.GENERATED
                }
                blocks += noteBlock {
                    id = 1
                    content = "bbb"
                    rendered = "<h1>bbb</h1>"
                    state = NoteBlock.block_state.GENERATED
                }

            }
            selectedStorageApi.value!!.setFileContents(
                arrayOf("notes").plus(note.pathList),
                "content",
                text.toString()
            )
        } else {
            currentNote = NoteContent.parseFrom(text.toString().toByteArray(Charset.defaultCharset()))
        }

        Column(Modifier.padding(it)) {
            val generated_blocks_list: HashMap<Int, NoteBlock> = hashMapOf()
            currentNote!!.blocksList.forEach { block ->
                generated_blocks_list[block.id] = block
            }
            val sorted_blocks =
                generated_blocks_list.toSortedMap(compareBy { it })
            sorted_blocks.forEach { block ->
                ViewBlock(
                    block = blockPlugins[noteType!!.blocksList[block.value.id].blockType]!!,
                    data = block.value.content,
                    config = ""
                )
            }
        }
    }
}