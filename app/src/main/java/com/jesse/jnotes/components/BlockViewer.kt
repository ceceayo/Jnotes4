package com.jesse.jnotes.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jesse.jnotes.logic.BlockPlugin
import com.jesse.jnotes.plugins.blocks.TextRenderBlock


// args will become block: BlockPlugin, data: String, config: String
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewBlock(block: BlockPlugin, data: String, config: String) {
    val state = rememberPagerState(pageCount = {3})
    HorizontalPager(state = state) {page ->
        when (page) {
            0 -> block.Block(content = data, config = config)
            1 -> Text(block.as_html(content = data, config = config))
            2 -> Text("To be implemented")
        }
    }
}

@Composable
@Preview
fun test_view_block() {
    ViewBlock(block = TextRenderBlock, data = "Jesse is fantastisch", config = "")
}