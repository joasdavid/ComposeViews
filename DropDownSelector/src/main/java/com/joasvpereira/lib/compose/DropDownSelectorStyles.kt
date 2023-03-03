package com.joasvpereira.lib.compose

/**
 * BorderStyles for [DropdownSelector] depending on the [DropdownSelectorState].
 *
 * @param unFocus style for when [DropdownSelector] is unFocus.
 * @param focus style for when [DropdownSelector] is focus.
 * @param error style for when [DropdownSelector] is error.
 * @param disable style for when [DropdownSelector] is disable.
 */
data class DropDownSelectorStyles(
    val unFocus: BorderStyle,
    val focus: BorderStyle,
    val error: BorderStyle,
    val disable: BorderStyle,
)