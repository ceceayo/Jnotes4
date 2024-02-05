package com.jesse.jnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.noteContent
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ViewFilePage(
    file: Int, config: MutableState<ConfigData?>, selectedStorageApi: MutableState<StorageApi?>
) {
    val note = config.value!!.notesList[file]
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(config.value!!.notesList[file].name.capitalize(Locale.current))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }) {
        var text = selectedStorageApi.value!!.getFileContents(
            arrayOf("notes").plus(note.pathList),
            "content"
        )
        if ((text == null) or (text == "")) {
            text = noteContent {
                title = note.name
                type = ""
            }.toString()
            selectedStorageApi.value!!.setFileContents(
                arrayOf("notes").plus(note.pathList),
                "content",
                text
            )
        }
        Column(Modifier.padding(it)) {
            Text(text!!)
        }
    }
}