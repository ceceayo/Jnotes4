package com.jesse.jnotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.jesse.jnotes.logic.StorageApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesPage(selectedStorageApi: MutableState<StorageApi?>) {
    Scaffold() {
        Column(Modifier.padding(it)) {
            Text("aaaa")
            var files = selectedStorageApi.value!!.getFileContents(emptyArray(), "files.csv")
            if (files == null) {
                files = ""
            }
            val rows: List<List<String>> = csvReader().readAll(files)
            rows.forEach { row ->
                Text(row[0])
            }

        }
    }
}