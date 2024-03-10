package com.jesse.jnotes.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.components.DropDownMenuComponent
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.logic.blockPlugins
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.configData
import com.jesse.jnotes.proto.configNoteTypeBlock
import com.jesse.jnotes.proto.noteType
import com.jesse.jnotes.views.destinations.FilesPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import jp.kaleidot725.texteditor.state.TextEditorState
import jp.kaleidot725.texteditor.view.TextEditor
import java.util.UUID


data class ConfigNewVaultPageNoteBlockType(
    var title: String, var block: String, var config: String
)

data class ConfigNewVaultPageNoteType(
    var title: String,
    var blocks: MutableList<ConfigNewVaultPageNoteBlockType>,
    val randomUnderTheHoodUUID: UUID
)


@Destination(
)
@Composable
fun ConfigNewVaultPage(
    nav: DestinationsNavigator,
    fileAccessPlugins: HashMap<String, StorageApi>,
    storageApiValue: String?,
    setStorageApi: (String) -> Unit,
    selectedStorageApi: MutableState<StorageApi?>,
    config: MutableState<ConfigData?>,
) {
    val scrollState = rememberScrollState()
    //val (storageApiValue, setStorageApi) = remember { mutableStateOf("-- please select a storage api --") }
    var configNoteTypeEditorState = remember { mutableStateListOf<ConfigNewVaultPageNoteType>() }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Icon(
            painter = painterResource(android.R.drawable.ic_menu_preferences),
            contentDescription = "Configuration For JNotes",
            modifier = Modifier
                .fillMaxWidth()
                .height(Dp(64F))
        )
        Text("Pre Launch Configuration for JNotes", Modifier.align(Alignment.CenterHorizontally))

        Text("Storage api: $storageApiValue")
        DropDownMenuComponent(content = fileAccessPlugins.keys.toList(), value = setStorageApi)

        if (storageApiValue in fileAccessPlugins.keys) {
            fileAccessPlugins[storageApiValue]!!.ConfigComponent()
        }

        Text("Note Types:")

        Column(
            Modifier
                .fillMaxWidth()
                .padding(Dp(16F))
                .background(Color(0.9F, 0.9F, 0.9F, 1F))
                .padding(Dp(8F))
        ) {
            Row(Modifier.fillMaxWidth()) {
                Button(onClick = {
                    configNoteTypeEditorState += listOf(
                        ConfigNewVaultPageNoteType(
                            title = "new note type",
                            blocks = mutableStateListOf(),
                            randomUnderTheHoodUUID = UUID.randomUUID()
                        )
                    )
                }) {
                    Text("Create a new noteType")
                }
            }
            configNoteTypeEditorState.forEachIndexed { idx, noteType ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(Dp(8F))
                        .background(Color(0.8f, 0.8f, 0.8f, 1f))
                ) {
                    //Text(noteType.title)
                    TextField(value = noteType.title, onValueChange = { newTitle ->
                        configNoteTypeEditorState[idx] = noteType.copy(title = newTitle)
                    },
                        Modifier
                            .fillMaxWidth()
                            .padding(Dp(4F)))
                    Row(Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            configNoteTypeEditorState[idx].blocks += ConfigNewVaultPageNoteBlockType(
                                title = "-- please enter a name for this block --",
                                "-- please select a block type --",
                                "-- please type any config for this block here, if needed --"
                            )
                        }, Modifier.padding(Dp(4F))) {
                            Text("Add Block")
                        }
                        Button(onClick = {
                            configNoteTypeEditorState -= listOf(noteType).toSet()
                        }, Modifier.padding(Dp(4F))) {
                            Text("Delete Type")
                        }
                    }
                    Text("Blocks:")

                    noteType.blocks.forEachIndexed { idx, blockType ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(Dp(4F))
                                .background(Color(0.7f, 0.7f, 0.7f, 1f))
                        ) {
                            TextField(value = blockType.title, onValueChange = { newName ->
                                noteType.blocks[idx] = blockType.copy(title = newName)
                            },
                                Modifier
                                    .fillMaxWidth()
                                    .padding(Dp(4F)))
                            Text("Type: ${blockType.block}")
                            DropDownMenuComponent(content = blockPlugins.keys.toList()) { blockName ->
                                noteType.blocks[idx] = blockType.copy(block = blockName)
                            }
                            Text("Config:")
                            var textEditorState by remember {
                                mutableStateOf(
                                    TextEditorState.create(
                                        blockType.config
                                    )
                                )
                            }
                            TextEditor(textEditorState = textEditorState,
                                onChanged = { newState ->
                                    textEditorState = newState
                                    noteType.blocks[idx] =
                                        blockType.copy(config = textEditorState.getAllText())
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .height(Dp(300F)),
                                decorationBox = { index, isSelected, innerTextField ->
                                    val backgroundColor =
                                        if (isSelected) Color.Gray else Color.Transparent
                                    Row(modifier = Modifier.background(backgroundColor)) {
                                        Text(
                                            text = (index + 1).toString().padStart(4, '0'),
                                            fontFamily = FontFamily.Monospace,
                                        )
                                        Spacer(modifier = Modifier.width(Dp(4F)))
                                        innerTextField(Modifier.fillMaxWidth())
                                    }
                                })
                        }
                    }
                }

            }
        }

        Button(
            onClick = {
                configNoteTypeEditorState.forEach { noteType ->
                    noteType.blocks.forEach { blockType ->
                        if(!blockPlugins.containsKey(blockType.block)) {
                            return@Button
                        }
                    }
                }
                if ((storageApiValue in fileAccessPlugins.keys) and (fileAccessPlugins[storageApiValue]?.isReady() == true)) {
                    selectedStorageApi.value = fileAccessPlugins[storageApiValue]!!
                    //config.value = dataConfigurationObject
                    config.value = configData {
                        fileStorage = storageApiValue!!
                        fileStorageOptions = fileAccessPlugins[storageApiValue]!!.saveConfig()
                        configNoteTypeEditorState.forEach { NoteTypeIt ->
                            notetypes += noteType {
                                name = NoteTypeIt.title
                                NoteTypeIt.blocks.forEach { NoteBlockIt ->
                                    blocks += configNoteTypeBlock {
                                        name = NoteBlockIt.title
                                        blockType = NoteBlockIt.block
                                        this.config = NoteBlockIt.config
                                    }
                                }
                            }
                        }
                    }
                    config.value!!.writeTo(
                        selectedStorageApi.value!!.setBinFileContents(arrayOf(), "config")
                            .outputStream()
                    )
                    nav.navigate(FilesPageDestination)
                }
            },
            Modifier
                .fillMaxWidth()
                .padding(Dp(24F))
        ) {
            Text("Go!")
        }
    }
}