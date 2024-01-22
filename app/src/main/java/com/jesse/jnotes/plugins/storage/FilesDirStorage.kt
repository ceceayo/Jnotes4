package com.jesse.jnotes.plugins.storage

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jesse.jnotes.logic.StorageApi
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class FilesDirStorage () : StorageApi {
    private var context: Context? = null
    override fun giveContext(context_arg: Context) {
        context = context_arg
        println(context_arg.filesDir)
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


    override fun getFileContents(folders: Array<String>, path: String): String {
        Files.createDirectories(Paths.get(context!!.filesDir.toString(), folders.joinToString("/", "", "/")))
        var calculatedPath = folders.joinToString("/", "", "/") + path
        if (calculatedPath[0] == '/') {
            calculatedPath = calculatedPath.drop(1)
        }
        val f = File(context!!.filesDir, calculatedPath)
        assert(f.isFile)
        assert(f.canRead())
        assert(f.exists())
        val s = f.readText(Charset.defaultCharset())
        println(s)
        return s
    }

    override fun setFileContents(folders: Array<String>, path: String, contents: String) {
        Files.createDirectories(Paths.get(context!!.filesDir.toString(), folders.joinToString("/", "", "/")))
        var calculatedPath = folders.joinToString("/", "", "/") + path
        if (calculatedPath[0] == '/') {
            calculatedPath = calculatedPath.drop(1)
        }
        val f = File(context!!.filesDir, calculatedPath)
        val w = f.writer(Charset.defaultCharset())
        w.write(contents)
        w.close()
    }

}