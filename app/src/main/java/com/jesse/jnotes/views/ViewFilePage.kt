package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.jesse.jnotes.components.ViewBlock
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.logic.blockPlugins
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.NoteBlock
import com.jesse.jnotes.proto.NoteContent
import com.jesse.jnotes.proto.NoteType
import com.jesse.jnotes.proto.noteBlock
import com.jesse.jnotes.proto.noteContent
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
    val scrollState = rememberScrollState()
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
            arrayOf("notes").plus(note.pathList).plus(note.name),
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
            val f = selectedStorageApi.value!!.setBinFileContents(
                arrayOf("notes").plus(note.pathList).plus(arrayOf(note.name)),
                "content",
            )
            currentNote.writeTo(f.outputStream())

        } else {
            //currentNote = NoteContent.parseFrom(text.toString().toByteArray(Charset.defaultCharset()))
            val f = text.toString().toByteArray(Charset.defaultCharset())
            val builder = NoteContent.newBuilder()
            currentNote = builder.mergeFrom(f).build()

        }

        Column(
            Modifier
                .padding(it)
                .verticalScroll(scrollState)) {
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
                    config = noteType!!.blocksList[block.value.id].config,
                    blockValue = block.value,
                    note = note,
                    globalConfig = config,
                    selectedStorageApi = selectedStorageApi,
                    currentNote = currentNote,
                )
            }
        }
    }
}