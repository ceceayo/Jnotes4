package com.jesse.jnotes.logic

import com.jesse.jnotes.plugins.storage.FilesDirStorage

// plugManager - plugin manager
// NOT a buttplug manager

val fileAccessPlugins = hashMapOf<String, StorageApi>(
    Pair("FilesDirStorage", FilesDirStorage())
)

val blockPlugins = hashMapOf<String, BlockPlugin>(

)