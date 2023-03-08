package com.joasvpereira.lib.compose.namedshield.strategy

interface TextHighlightStrategy {
    fun capture(from: String): String
}