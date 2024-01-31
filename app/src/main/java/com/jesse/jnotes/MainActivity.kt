package com.jesse.jnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.logic.fileAccessPlugins
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.ui.theme.JnotesTheme
import com.jesse.jnotes.views.ConfigPage
import com.jesse.jnotes.views.FilesPage

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        for (storageApi in fileAccessPlugins) {

            storageApi.value.giveContext(applicationContext)
            storageApi.value.setFileContents(arrayOf("mydir", "nested"),"test.txt", "All systems go for storageapi!!!!")
            println(storageApi.value.getFileContents(arrayOf("mydir", "nested"), "test.txt"))
        }
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()
            val (storageApiValue, setStorageApi) = remember { mutableStateOf("-- please select a storage api --") }
            var selectedStorageApi = remember { mutableStateOf<StorageApi?>(null) }
            var config = remember { mutableStateOf<ConfigData?>(null)}
            JnotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(nav, "config") {
                        composable("config") { ConfigPage(nav, fileAccessPlugins, storageApiValue, setStorageApi, selectedStorageApi, config) }
                        composable("home") { FilesPage(config) }
                    }
                }
            }
        }
    }
}
