package com.joasvpereira.lib.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp


/**
 * Out of the box values used in [DropdownSelector]
 */
object DropDownSelectorDefaults {

    /**
     * Default BorderStyles for [DropdownSelector], each style can be override by replace the default parameter.
     *
     * @param unFocus style for when [DropdownSelector] is unFocus.
     * @param focus style for when [DropdownSelector] is focus.
     * @param error style for when [DropdownSelector] is error.
     * @param disable style for when [DropdownSelector] is disable.
     */
    @Composable
    fun defaultBorderStyle(
        unFocus: BorderStyle  = BorderStyle(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = MaterialTheme.shapes.extraSmall
        ),
        focus: BorderStyle  = BorderStyle(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.extraSmall
        ),
        error: BorderStyle  = BorderStyle(
            width = 2.dp,
            color = MaterialTheme.colorScheme.error,
            shape = MaterialTheme.shapes.extraSmall
        ),
        disable: BorderStyle  = BorderStyle(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = DISABLED_ALPHA),
            shape = MaterialTheme.shapes.extraSmall
        ),
    ): DropDownSelectorStyles = DropDownSelectorStyles(
        unFocus = unFocus,
        focus = focus,
        error = error,
        disable = disable,
    )

    /**
     * Default indicator used on [DropdownSelector], it will use the [ArrowDropDown] as icon to be draw and a space between content of 5 Dp
     */
    @Composable
    fun defaultIconIndicator() : DropdownIcon {
        return DropdownIcon(
            painter = rememberVectorPainter(Icons.Filled.ArrowDropDown),
            contentDescription = "Arrow Down",
            space = 5.dp
        )
    }

    /**
     * Alpha value for disabled style
     */
    const val DISABLED_ALPHA = .25F
}