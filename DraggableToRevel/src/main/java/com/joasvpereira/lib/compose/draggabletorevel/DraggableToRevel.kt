package com.joasvpereira.lib.compose.draggabletorevel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberDraggableToRevelState(
    offsetX: Float = 0f,
    direction: RevelDirection = RevelDirection.LEFT,
): DraggableToRevelState {
    return DraggableToRevelState(
        offsetXInit = offsetX,
        direction = direction,
    )
}

@Composable
fun DraggableToRevel(
    modifier: Modifier = Modifier,
    draggableToRevelState: DraggableToRevelState = rememberDraggableToRevelState(),
    contentBehindColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(0.dp),
    contentBehind: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    // Get local density from composable
    val localDensity = LocalDensity.current

    // Create element height in pixel state
    var columnHeightPx by remember {
        mutableStateOf(0f)
    }
    val maxHeight = with(localDensity) { columnHeightPx.toDp() }
    Box {
        maxHeight.takeIf { it > 0.dp }?.let { height ->
            Surface(
                modifier = modifier.clip(shape),
                color = contentBehindColor,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .height(height)
                            // .background(Color.Green.copy(alpha = .25f))
                            .onGloballyPositioned { coordinates ->
                                // Set column height using the LayoutCoordinates
                                draggableToRevelState.maxGap = coordinates.size.width.toFloat()
                            }
                            .align(
                                if (draggableToRevelState.direction == RevelDirection.RIGHT) {
                                    Alignment.CenterEnd
                                } else {
                                    Alignment.CenterStart
                                },
                            ),
                    ) {
                        contentBehind()
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                // .offset { IntOffset(offsetX.roundToInt(), 0) }
                .offset { draggableToRevelState.provideIntOffset() }
                .draggable(
                    state = draggableToRevelState.draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStarted = {
                        draggableToRevelState.isDragging = true
                    },
                    onDragStopped = {
                        draggableToRevelState.isDragging = false
                        draggableToRevelState.endPosition()
                    },
                )
                .onGloballyPositioned { coordinates ->
                    // Set column height using the LayoutCoordinates
                    columnHeightPx = coordinates.size.height.toFloat()
                }
                .clip(shape)
                .then(modifier),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun DraggableToRevelPreview() {
        Column {
            val draggableToRevelState = rememberDraggableToRevelState(offsetX = 0f)
            DraggableToRevel(
                shape = RoundedCornerShape(50.dp),
                draggableToRevelState = draggableToRevelState,
                contentBehindColor = MaterialTheme.colorScheme.secondary,
                contentBehind = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(100.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(100.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    }
                },
            ) {
                val scope: CoroutineScope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                draggableToRevelState.toggle()
                            }
                        },
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 50.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Drag me. or click me to reveal content",
                        )
                    }
                }
            }
        }
}
