package com.joasvpereira.lib.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp

/**
 * Data for the indicator icon on [DropdownSelector].
 *
 * @param painter [Painter] to provide what should be draw.
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes, and
 * does not represent a meaningful action that a user can take. This text should be localized, such
 * as by using [androidx.compose.ui.res.stringResource] or similar.
 * @param space space in [Dp] between the [DropdownSelector] content and the indicator icon that will be draw using the [painter].
 * @param tint tint to be applied to [painter]. If null is provided, then no tint
 * is applied.
 */
data class DropdownIcon(
    val painter: Painter,
    val contentDescription: String,
    val space: Dp,
    val tint: Color? = null
)