package com.jesse.jnotes.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jesse.jnotes.logic.BlockPlugin
import com.jesse.jnotes.logic.StorageApi
import com.jesse.jnotes.proto.ConfigData
import com.jesse.jnotes.proto.Note
import com.jesse.jnotes.proto.NoteBlock
import com.jesse.jnotes.proto.NoteContent


// args will become block: BlockPlugin, data: String, config: String
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewBlock(
    block: BlockPlugin,
    data: String,
    config: String,
    blockValue: NoteBlock,
    note: Note,
    globalConfig: MutableState<ConfigData?>,
    selectedStorageApi: MutableState<StorageApi?>,
    currentNote: NoteContent
) {
    val state = rememberPagerState(pageCount = {3})
    HorizontalPager(state = state,
        Modifier
            .height(Dp(300F))
            .padding(Dp(12F))) { page ->
        when (page) {
            0 -> block.Block(content = data, config = config)
            1 -> WebView(block.as_html(data, config))
            2 -> BlockEditor(blockValue, note, globalConfig, selectedStorageApi, currentNote)
        }
    }
}