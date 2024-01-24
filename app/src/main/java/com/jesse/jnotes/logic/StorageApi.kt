package com.jesse.jnotes.logic

import android.content.Context
import androidx.compose.runtime.Composable

interface StorageApi {

    // Setup
    fun giveContext(context_arg: Context)
    @Composable
    fun ConfigComponent()
    fun isReady(): Boolean
    fun loadSavedConfig(savedConfig: String)
    fun saveConfig(): String

    // File and folder access
    fun getFileContents(folders: Array<String>, path: String): String?
    fun setFileContents(folders: Array<String>, path: String, contents: String)
}