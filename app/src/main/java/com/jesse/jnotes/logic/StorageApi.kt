package com.jesse.jnotes.logic

import androidx.compose.runtime.Composable

interface StorageApi {
    // Setup
    @Composable
    fun ConfigComponent()
    fun isReady(): Boolean
    fun loadSavedConfig(savedConfig: String)
    fun saveConfig(): String

    // File and folder access
    fun listDirectory(path: String)
    fun getFileContents(path: String): String
    fun setFileContents(path: String, contents: String)
}