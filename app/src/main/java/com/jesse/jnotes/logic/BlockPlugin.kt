package com.jesse.jnotes.logic

import androidx.compose.runtime.Composable

/**
 * Block plugin
 * A plugin which renders a block
 */
interface BlockPlugin {
    /**
     * Block
     *
     * @param content
     */
    @Composable
    fun Block(content: String, config: String) : Unit


    /**
     * Is_valid_content
     *
     * @param content
     * @return
     */
    fun is_valid_content(content: String, config: String) : String?
}