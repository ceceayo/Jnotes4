package com.jesse.jnotes.plugins.storage

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jesse.jnotes.logic.StorageApi

class FilesDirStorage () : StorageApi {
    private var context: Context? = null
    override fun giveContext(context_arg: Context) {
        context = context_arg
    }
    @Composable
    override fun ConfigComponent() {
        Text("all ready to go, mate")
    }

    override fun isReady(): Boolean {
        return context != null
    }

    override fun loadSavedConfig(savedConfig: String) {
        return
    }

    override fun saveConfig(): String {
        return ""
    }

    override fun listDirectory(path: String) {
        TODO("Not yet implemented")
    }

    override fun getFileContents(path: String): String {
        TODO("Not yet implemented")
    }

    override fun setFileContents(path: String, contents: String) {
        TODO("Not yet implemented")
    }


}