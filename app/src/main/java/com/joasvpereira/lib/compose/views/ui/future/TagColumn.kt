package com.joasvpereira.lib.compose.views.ui.future
/*

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class TagColumnState(var isEditOpen: Boolean = false, val listOfTags: MutableList<String> = mutableListOf())

@Composable
fun TagColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    tagItemContent: @Composable ColumnScope.(String) -> Unit,
    addButton: @Composable ColumnScope.() -> Unit,
    editorContent: @Composable ColumnScope.() -> Unit,
    tagColumnState: TagColumnState,
) {
    Column(modifier = modifier, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment) {
        if (tagColumnState.isEditOpen) {
            editorContent()
        } else {
            addButton()
        }
        tagColumnState.listOfTags.forEach {
            tagItemContent(it)
        }
    }
}

@Preview()
@Composable
fun TagColumnPreview() {
    var tagColumnState by remember { mutableStateOf(TagColumnState(isEditOpen = true, listOfTags = mutableListOf("test", "XPTO", "test3"))) }
    val roundedCornerShape200 = RoundedCornerShape(200.dp)
    TagColumn(
        tagItemContent = { s: String ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)

                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = roundedCornerShape200,
                    )
                    .clip(roundedCornerShape200)
                    .padding(5.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = s,
                )
            }
        },
        addButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(200.dp),
                    )
                    .padding(5.dp),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        editorContent = {},
        tagColumnState = tagColumnState,
    )
}
*/