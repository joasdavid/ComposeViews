package com.joasvpereira.lib.compose.spacer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSpacer(size: Dp) {
    Spacer(modifier = Modifier.size(size))
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Preview
@Composable
private fun SimpleSpacerPreview() {
    Column(modifier = Modifier.background(Color.Red)) {
        SimpleSpacer(size = 45.dp)
        Row {
            Box(modifier = Modifier.size(25.dp).background(Color.Green))
            HorizontalSpacer(width = 25.dp)
            Box(modifier = Modifier.size(25.dp).background(Color.Green))
        }
        VerticalSpacer(height = 45.dp)
    }
}