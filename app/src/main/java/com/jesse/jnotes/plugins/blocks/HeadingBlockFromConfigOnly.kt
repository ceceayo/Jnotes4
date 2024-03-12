package com.jesse.jnotes.plugins.blocks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.jesse.jnotes.logic.BlockPlugin

object HeadingBlockFromConfigOnly : BlockPlugin {
    @Composable
    override fun Block(content: String, config: String) {
        Text(config, textAlign = TextAlign.Center, fontSize = TextUnit(35F, TextUnitType.Sp))
    }
    override fun as_html(content: String, config: String): String {
        return "<h1 style=\"color:red;\">$config</h1>"
    }
    override fun is_valid_content(content: String, config: String): String? {
        return null
    }
}