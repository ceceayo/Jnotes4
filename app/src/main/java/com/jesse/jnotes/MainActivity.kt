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
import androidx.navigation.compose.rememberNavController
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.logic.fileAccessPlugins
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.ui.theme.JnotesTheme
import com.jesse.jnotes.views.ConfigNewVaultPage
import com.jesse.jnotes.views.ConfigPage
import com.jesse.jnotes.views.FilesPage
import com.jesse.jnotes.views.NavGraphs
import com.jesse.jnotes.views.destinations.ConfigNewVaultPageDestination
import com.jesse.jnotes.views.destinations.ConfigPageDestination
import com.jesse.jnotes.views.destinations.FilesPageDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable

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
                ) {/*
                    NavHost(nav, "config") {
                        composable("config") { ConfigPage(nav) }
                        composable("config/new_vault") { ConfigNewVaultPage(nav, fileAccessPlugins, storageApiValue, setStorageApi, selectedStorageApi, config) }
                        composable("home") { FilesPage(config, nav) }
                        composable("note/{note}", arguments = listOf(navArgument("note") { type = NavType.StringArrayType })) { backStackEntry -> backStackEntry.arguments?.getString("note")
                            ?.let { Text(it as String) } }
                    }*/
                    DestinationsNavHost(navGraph = NavGraphs.root) {
                        composable(ConfigPageDestination) {
                            ConfigPage(
                                navigator = destinationsNavigator,
                                config = config
                            )
                        }
                        composable(ConfigNewVaultPageDestination) {
                            ConfigNewVaultPage(
                                nav = destinationsNavigator,
                                fileAccessPlugins = fileAccessPlugins,
                                storageApiValue = storageApiValue,
                                setStorageApi = setStorageApi,
                                selectedStorageApi = selectedStorageApi,
                                config = config
                            )
                        }
                        composable(FilesPageDestination) {
                            FilesPage(config = config, nav = destinationsNavigator)
                        }
                    }
                }
            }
        }
    }
}
