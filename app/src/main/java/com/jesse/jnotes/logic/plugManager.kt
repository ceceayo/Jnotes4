package com.jesse.jnotes.logic

import com.jesse.jnotes.plugins.blocks.HeadingBlockFromConfigOnly
import com.jesse.jnotes.plugins.blocks.TextRenderBlock
import com.jesse.jnotes.plugins.storage.FilesDirStorage

// plugManager - plugin manager
// NOT a buttplug manager

val fileAccessPlugins = hashMapOf<String, StorageApi>(
    Pair("FilesDirStorage", FilesDirStorage())
)

val blockPlugins = hashMapOf<String, BlockPlugin>(
    Pair("TextRenderBlock", TextRenderBlock),
    Pair("HeadingBlockFromConfigOnly", HeadingBlockFromConfigOnly),
)