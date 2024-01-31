package com.jesse.jnotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.google.protobuf.kotlin.DslList
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.R
import com.jesse.jnotes.components.DropDownMenuComponent
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigDataKt
import com.jesse.jnotes.proto.Note
import com.jesse.jnotes.proto.configData
import com.jesse.jnotes.proto.note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigPage(
    nav: NavController,
    fileAccessPlugins: HashMap<String, StorageApi>,
    storageApiValue: String,
    setStorageApi: (String) -> Unit,
    selectedStorageApi: MutableState<StorageApi?>,
    config: MutableState<ConfigData?>
) {
    val scrollState = rememberScrollState()
    //val (storageApiValue, setStorageApi) = remember { mutableStateOf("-- please select a storage api --") }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Configuration For JNotes",
            modifier = Modifier.fillMaxWidth()
        )
        Text("Pre Launch Configuration for JNotes", Modifier.align(Alignment.CenterHorizontally))

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
                    //config.value = dataConfigurationObject
                    config.value = configData {
                        fileStorage = storageApiValue
                        fileStorageOptions = fileAccessPlugins[storageApiValue]!!.saveConfig()
                        notes += note {
                            path += "a"
                            name = "my file"
                        }
                    }
                    nav.navigate("home")
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