package com.jesse.jnotes.views

import java.nio.charset.Charset
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.components.DropDownMenuComponent
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.configData
import com.jesse.jnotes.proto.configNoteTypeBlock
import com.jesse.jnotes.proto.note
import com.jesse.jnotes.proto.noteType
import com.jesse.jnotes.views.destinations.FilesPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
)
@Composable
fun ConfigLoadVaultPage(
    nav: DestinationsNavigator,
    fileAccessPlugins: HashMap<String, StorageApi>,
    storageApiValue: String?,
    setStorageApi: (String) -> Unit,
    selectedStorageApi: MutableState<StorageApi?>,
    config: MutableState<ConfigData?>,
) {
    val scrollState = rememberScrollState()
    //val (storageApiValue, setStorageApi) = remember { mutableStateOf("-- please select a storage api --") }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Icon(
            painter = painterResource(android.R.drawable.ic_menu_preferences),
            contentDescription = "Configuration For JNotes",
            modifier = Modifier.fillMaxWidth().height(Dp(64F))
        )
        Text("Load Vault Bitte Bitte", Modifier.align(Alignment.CenterHorizontally))

        Text("Storage api: $storageApiValue")
        DropDownMenuComponent(content = fileAccessPlugins.keys.toList(), value = setStorageApi)

        if (storageApiValue in fileAccessPlugins.keys) {
            fileAccessPlugins[storageApiValue]!!.ConfigComponent()
        }

        Button(
            onClick = {
                if ((storageApiValue in fileAccessPlugins.keys) and (
                            fileAccessPlugins[storageApiValue]?.isReady() == true
                            )
                ) {
                    selectedStorageApi.value = fileAccessPlugins[storageApiValue]!!
                    val text = selectedStorageApi.value!!.getFileContents(
                        arrayOf(),
                        "config"
                    )
                    val f = text.toString().toByteArray(Charset.defaultCharset())
                    val builder = ConfigData.newBuilder()
                    config.value = builder.mergeFrom(f).build()

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