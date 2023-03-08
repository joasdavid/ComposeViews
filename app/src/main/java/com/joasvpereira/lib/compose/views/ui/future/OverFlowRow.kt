package com.joasvpereira.lib.compose.views.ui.future
/*

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun OverFlowRow(
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 0.dp,
    overFlowBadge: @Composable (String) -> Unit = {
        Text(
            text = it,
            style = MaterialTheme.typography.labelSmall,
        )
    },
    content: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier,
    ) { constraints ->

        val placeables = subcompose("01", content).map { it.measure(constraints) }
        val placeHolder = subcompose("00") { overFlowBadge("+99") }.map { it.measure(constraints) }.first()

        val maxWidth = constraints.maxWidth - placeHolder.width
        val placeablesHeight = if (placeables.isNotEmpty()) placeables.maxOf { it.height } else 0
        val maxHeight = max(placeablesHeight, placeHolder.height)

        var countOfItemsNotShown = placeables.size
        val nrOfItems = placeables.run {
            var size = 0
            for (placeable in placeables) {
                if (size + placeable.width > maxWidth) break
                size += placeable.width + (spaceBetween.toPx().toInt())
                countOfItemsNotShown--
            }
            when {
                countOfItemsNotShown > 99 -> 99
                countOfItemsNotShown < 0 -> 0
                else -> countOfItemsNotShown
            }
        }
        val pp = subcompose("03") { overFlowBadge("+$nrOfItems") }.map { it.measure(constraints) }.first()

        var counter = 0

        layout(maxWidth, maxHeight) {
            var xPosition = 0
            for (placeable in placeables) {
                if (xPosition + placeable.width > maxWidth) break
                placeable.placeRelative(x = xPosition, 0)
                xPosition += placeable.width + (spaceBetween.toPx().toInt())
                counter++
            }
            if (countOfItemsNotShown > 0) {
                pp.placeRelative(xPosition, 0)
            }
        }
    }
}
*/
