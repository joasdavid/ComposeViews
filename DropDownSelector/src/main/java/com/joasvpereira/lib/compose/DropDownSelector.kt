package com.joasvpereira.lib.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

private fun Modifier.border(parameters: BorderStyle) =
    this.border(
        width = parameters.width,
        color = parameters.color,
        shape = parameters.shape
    )

/**
 * Generic dropdown selector that provides a drop down selector as it menu behavior and delegates the UI by two composable parameters.
 *
 * @param modifier an optional [Modifier].
 * @param selectionOpenState state that indicates if should draw the dropdown expandable content.
 * @param onSelectionOpenStateChanges state changes for the dropdown.
 * @param dropdownIcon an optional nullable [DropdownIcon] to be used to draw the icon indicator, when null no icon will be draw. If no [DropdownIcon] is provided
 * [DropDownSelectorDefaults.defaultIconIndicator] will be used.
 * @param dropDownSelectorStyles an optional [DropDownSelectorStyles] that will provide the styles to be used. If no [DropDownSelectorStyles] is provided [DropDownSelectorDefaults.defaultBorderStyle]
 * will be used.
 * @param isError indicated that should be applied the error style to this composable.
 * @param isDisabled indicated that should be applied the disabled style to this composable.
 * @param elements list of [T] elements that ar the options of this dropdown.
 * @param previewContent content within the borders of the dropdown selector. The lambda receives a nullable [T] that is the selected element and a [DropdownSelectorState] witch is the current state
 * of the dropdown.
 * @param selectedElement an optional nullable [T] that will be provided to the [previewContent]
 * @param onSelectedElementChanges called when a new element is selected on the dropdown.
 * @param expandableContent dropdown menu content.
 */
@Composable
fun <T> DropdownSelector(
    modifier: Modifier = Modifier,
    selectionOpenState: Boolean,
    onSelectionOpenStateChanges: (Boolean) -> Unit,
    dropdownIcon: DropdownIcon? = DropDownSelectorDefaults.defaultIconIndicator(),
    dropDownSelectorStyles: DropDownSelectorStyles = DropDownSelectorDefaults.defaultBorderStyle(),
    isError: Boolean = false,
    isDisabled: Boolean = false,
    elements: List<T>,
    previewContent: @Composable (T?, DropdownSelectorState) -> Unit,
    selectedElement: T? = elements.firstOrNull(),
    onSelectedElementChanges: (T) -> Unit,
    expandableContent: @Composable (T) -> Unit
) {
    val borderParameters = when {
        isError -> dropDownSelectorStyles.error
        isDisabled -> dropDownSelectorStyles.disable
        selectionOpenState -> dropDownSelectorStyles.focus
        else -> dropDownSelectorStyles.unFocus
    }
    val currentState = when {
        isDisabled -> DropdownSelectorState.DISABLE
        isError -> DropdownSelectorState.ERROR
        selectionOpenState -> DropdownSelectorState.ON_FOCUS
        else -> DropdownSelectorState.OUT_FOCUS
    }

    DropdownContainer(
        borderParameters,
        currentState,
        onSelectionOpenStateChanges,
        selectionOpenState,
        modifier,
        previewContent,
        selectedElement,
        dropdownIcon,
        dropDownSelectorStyles,
        elements,
        expandableContent,
        onSelectedElementChanges
    )
}

@Composable
private fun <T> DropdownContainer(
    borderParameters: BorderStyle,
    currentState: DropdownSelectorState,
    onSelectionOpenStateChanges: (Boolean) -> Unit,
    selectionOpenState: Boolean,
    modifier: Modifier,
    previewContent: @Composable (T?, DropdownSelectorState) -> Unit,
    selectedOption: T?,
    dropdownIcon: DropdownIcon?,
    dropDownSelectorStyles: DropDownSelectorStyles,
    options: List<T>,
    expandableContent: @Composable (T) -> Unit,
    onSelectedOptionChanges: (T) -> Unit
) {
    var parentSize by remember { mutableStateOf(Size.Zero) }
    Row(
        Modifier
            .border(borderParameters)
            .clickable {
                if (currentState != DropdownSelectorState.DISABLE)
                    onSelectionOpenStateChanges(!selectionOpenState)
            }
            .then(modifier)
            .onGloballyPositioned {
                parentSize = it.size.toSize()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropdownContent<T>(
            previewContent,
            selectedOption,
            currentState,
            dropdownIcon,
            dropDownSelectorStyles
        )
        ExpandableMenu(
            parentSize,
            selectionOpenState,
            onSelectionOpenStateChanges,
            options,
            expandableContent,
            onSelectedOptionChanges
        )
    }
}

@Composable
private fun <T> DropdownContent(
    previewContent: @Composable (T?, DropdownSelectorState) -> Unit,
    selectedOption: T?,
    currentState: DropdownSelectorState,
    dropdownIcon: DropdownIcon?,
    dropDownSelectorStyles: DropDownSelectorStyles
) {
    Row(Modifier.padding(8.dp)) {
        Box(Modifier.weight(1f)) {
            previewContent(selectedOption, currentState)
        }
        if (dropdownIcon != null) DropdownIconPlacer(
            dropdownIcon = dropdownIcon,
            dropDownSelectorStyles = dropDownSelectorStyles,
            state = currentState
        )
    }
}

/**
 * Will take [DropdownIcon] and convert it to [Icon] and assert the style from [DropDownSelectorStyles] should be used for the [DropdownSelectorState] provided.
 *
 * This compose will also animate the [Icon] when the [state] is [DropdownSelectorState.ON_FOCUS] by rotation the it.
 *
 * @param dropdownIcon icon information to be able to convert to a [Icon].
 * @param dropDownSelectorStyles styles to be able to tint the [Icon] accordingly.
 * @param state current [DropdownSelectorState], this will be used to choose the style to be applied on the [Icon].
 */
@Composable
fun DropdownIconPlacer(
    dropdownIcon: DropdownIcon,
    dropDownSelectorStyles: DropDownSelectorStyles,
    state: DropdownSelectorState
) {
    val tint = when (state) {
        DropdownSelectorState.ERROR -> dropDownSelectorStyles.error.color
        DropdownSelectorState.DISABLE -> dropDownSelectorStyles.disable.color
        else -> dropdownIcon.tint
    }
    val angle: Float by animateFloatAsState(targetValue = if (state == DropdownSelectorState.ON_FOCUS) 180f else 0f)

    Icon(
        modifier = Modifier
            .padding(start = dropdownIcon.space)
            .rotate(angle),
        painter = dropdownIcon.painter,
        contentDescription = dropdownIcon.contentDescription,
        tint = tint ?: LocalContentColor.current,
    )
}

/**
 * Generic dropdown menu displayed for a [DropdownSelector].
 *
 * @param parentSize size of parent compose.
 * @param selectionOpenState open state when true will draw the dropdown menu.
 * @param onSelectionOpenStateChanges called when the user dismiss the dropdown menu by tapping out outside the menu's bounds or by selecting a DropdownMenuItem.
 * @param element list of element matching the generic type defined on the function, for each one of them will be produce a DropdownMenuItem.
 * @param expandableContent composable function that diffine the UI for a element.
 * @param onSelectedOptionChanges called when a DropdownMenuItem is selected and provide the element of it
 */
@Composable
private fun <T> ExpandableMenu(
    parentSize: Size,
    selectionOpenState: Boolean,
    onSelectionOpenStateChanges: (Boolean) -> Unit,
    element: List<T>,
    expandableContent: @Composable (T) -> Unit,
    onSelectedOptionChanges: (T) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.width(with(LocalDensity.current) {
            parentSize.width.toDp()
        }),
        expanded = selectionOpenState,
        onDismissRequest = {
            onSelectionOpenStateChanges(false)
        }
    ) {
        element.forEach {
            DropdownMenuItem(text = { expandableContent(it) }, onClick = {
                onSelectedOptionChanges(it)
                onSelectionOpenStateChanges(!selectionOpenState)
            })
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000)
@Composable
private fun DropdownSelectorPreview() {
    var open by remember { mutableStateOf(false) }
    Column {
        DropdownSelector<Any>(
            selectionOpenState = open,
            onSelectionOpenStateChanges = {
                open = !open
            },
            elements = listOf(),
            previewContent = { _, _ ->
                Row {
                    Text(text = "this is a theme")
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                }
            },
            expandableContent = {

            },
            onSelectedElementChanges = {},
            isError = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownSelector<Any>(
            selectionOpenState = open,
            onSelectionOpenStateChanges = {
                open = !open
            },
            elements = listOf(),
            previewContent = { _, _ ->
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "this is a theme")
            },
            expandableContent = {

            },
            onSelectedElementChanges = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownSelector<Any>(
            selectionOpenState = open,
            onSelectionOpenStateChanges = {
                open = !open
            },
            isDisabled = true,
            elements = listOf(),
            previewContent = { _, state ->
                if (state != DropdownSelectorState.DISABLE)
                    Text(text = "this is a theme")
            },
            expandableContent = {

            },
            onSelectedElementChanges = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownSelector<Any>(
            selectionOpenState = open,
            isDisabled = true,
            onSelectionOpenStateChanges = {
                open = !open
            },
            elements = listOf(),
            previewContent = { _, _ ->
                Text(
                    text = "this is a theme",
                    //color = Color.Unspecified.copy(alpha = DISABLED_ALPHA)
                )
            },
            expandableContent = {

            },
            onSelectedElementChanges = {}, dropdownIcon = null, selectedElement = null
        )

        Spacer(modifier = Modifier.height(10.dp))
        DropdownSelector<Int>(
            selectionOpenState = false,
            isDisabled = false,
            onSelectionOpenStateChanges = { open = !open },
            elements = listOf(1,1,1,1,1,2),
            dropDownSelectorStyles = DropDownSelectorDefaults.defaultBorderStyle(unFocus = BorderStyle(1.dp, Color.Black, CircleShape)),
            previewContent = { _, _ ->
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "", modifier = Modifier.size(24.dp))
                }
            },
            expandableContent = {
                                Text(text = it.toString())
            },
            onSelectedElementChanges = {},
            dropdownIcon = DropDownSelectorDefaults.defaultIconIndicator().copy(space = 0.dp),
            selectedElement = 5054,
            modifier = Modifier
                .width(90.dp)
                .height(40.dp)
        )
    }
}