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


// args will become block: BlockPlugin, data: String, config: String
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun ViewBlock() {
    val state = rememberPagerState(pageCount = {3})
    HorizontalPager(state = state) {page ->
        Text("page $page", Modifier.fillMaxSize())
    }
}