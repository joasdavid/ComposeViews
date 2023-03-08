package com.joasvpereira.lib.compose.views.ui
/*

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ColorSchemeItem(
    val name: String,
    val color: Color,
    val onColorName: String? = null,
    val onColor: Color? = null,
)

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun ColorSchemeScreen() {
    val colorList = listOf(
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.primary,
            name = "primary",
            onColor = MaterialTheme.colorScheme.onPrimary,
            onColorName = "onPrimary",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.inversePrimary,
            name = "inversePrimary",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.primaryContainer,
            name = "primaryContainer",
            onColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onColorName = "onPrimaryContainer",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.secondary,
            name = "secondary",
            onColor = MaterialTheme.colorScheme.onSecondary,
            onColorName = "onSecondary",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.secondaryContainer,
            name = "secondaryContainer",
            onColor = MaterialTheme.colorScheme.onSecondaryContainer,
            onColorName = "onSecondaryContainer",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.tertiary,
            name = "tertiary",
            onColor = MaterialTheme.colorScheme.onTertiary,
            onColorName = "onTertiary",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            name = "tertiaryContainer",
            onColor = MaterialTheme.colorScheme.onTertiaryContainer,
            onColorName = "onTertiaryContainer",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.surface,
            name = "surface",
            onColor = MaterialTheme.colorScheme.onSurface,
            onColorName = "onSurface",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.inverseSurface,
            name = "inverseSurface",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.surfaceTint,
            name = "surfaceTint",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.surfaceVariant,
            name = "surfaceVariant",
            onColor = MaterialTheme.colorScheme.onSurfaceVariant,
            onColorName = "onSurfaceVariant",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.background,
            name = "background",
            onColor = MaterialTheme.colorScheme.onBackground,
            onColorName = "onBackground",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.error,
            name = "error",
            onColor = MaterialTheme.colorScheme.onError,
            onColorName = "onError",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.errorContainer,
            name = "errorContainer",
            onColor = MaterialTheme.colorScheme.onErrorContainer,
            onColorName = "onErrorContainer",
        ),
        ColorSchemeItem(
            color = MaterialTheme.colorScheme.outline,
            name = "outline",
        ),
    )

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(colorList.size) { index ->
            Column(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth()
                    .background(colorList[index].color),
            ) {
                Text(text = colorList[index].name)
                colorList[index].onColorName?.let {
                    Text(text = it, color = colorList[index].onColor!!)
                }
            }
        }
        item {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("OutlinedTextField") },
                maxLines = 2,
            )
        }
        item {
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("OutlinedTextField") },
                maxLines = 2,
            )
        }
        item {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Box")
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }

        item {
            Surface(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Surface")
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Card")
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Box")
                    TextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }

        item {
            Surface(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Surface")
                    TextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = "Card")
                    TextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("OutlinedTextField") },
                        maxLines = 2,
                    )
                }
            }
        }
        item {
            Button(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
                onClick = {},
            ) {
                Text(text = "Button")
            }
        }
        item {
            FloatingActionButton(
                modifier = Modifier
                    .size(78.dp)
                    .fillMaxWidth(),
                onClick = {},
            ) {
                Text(text = "FloatingActionButton")
            }
        }
    }
}
*/
