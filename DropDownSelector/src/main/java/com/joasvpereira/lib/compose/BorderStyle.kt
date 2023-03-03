package com.joasvpereira.lib.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * [BorderStyle] has the possible style attributes for a border.
 *
 * @param [width] of the border
 * @param [color] of the border
 * @param [shape] of the border
 */
data class BorderStyle(
    val width: Dp, val color: Color, val shape: Shape
)
