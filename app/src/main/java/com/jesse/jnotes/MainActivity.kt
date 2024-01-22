package com.jesse.jnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesse.jnotes.logic.fileAccessPlugins
import com.jesse.jnotes.ui.theme.JnotesTheme
import com.jesse.jnotes.views.ConfigPage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        for (storageApi in fileAccessPlugins) {

            storageApi.value.giveContext(applicationContext)
            storageApi.value.setFileContents(arrayOf("mydir"),"test.txt", "All systems go for storageapi!!!!")
            println(storageApi.value.getFileContents(arrayOf("mydir"), "test.txt"))
            //println(storageApi.value.listDirectory("")!!.joinToString(" ... "))
        }
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()
            JnotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(nav, "config") {
                        composable("config") { ConfigPage(nav, fileAccessPlugins) }
                        composable("home") { Text("Hi") }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFromHell() {
    val nav = rememberNavController()
    JnotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(nav, "config") {
                composable("config") { ConfigPage(nav, fileAccessPlugins) }
                composable("home") { Text("Hi") }
            }
        }
    }
}