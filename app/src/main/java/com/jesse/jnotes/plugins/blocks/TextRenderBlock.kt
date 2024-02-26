package com.jesse.jnotes.plugins.blocks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jesse.jnotes.logic.BlockPlugin

object TextRenderBlock : BlockPlugin {
    @Composable
    override fun Block(content: String, config: String) {
        Text(content)
    }
    override fun as_html(content: String, config: String): String {
        return "<p>$content</p>"
    }
    override fun is_valid_content(content: String, config: String): String? {
        return null
    }
}