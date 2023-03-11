package com.joasvpereira.lib.compose.draggabletorevel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
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
private fun BehindContainer(
    modifier: Modifier,
    shape: Shape,
    contentBehindColor: Color,
    draggableToRevelState: DraggableToRevelState,
    contentBehind: @Composable() (RowScope.() -> Unit)
) {
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
                    .onGloballyPositioned { coordinates ->
                        // Set column height using the LayoutCoordinates
                        draggableToRevelState.maxDrag = coordinates.size.width.toFloat()
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

@Composable
private fun FrontContainer(
    draggableToRevelState: DraggableToRevelState,
    shape: Shape,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
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
            .clip(shape)
            .then(modifier),
    ) {
        content()
    }
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
    Layout(
        modifier = modifier.then(Modifier.height(IntrinsicSize.Max)),
        content = {
            BehindContainer(
                modifier = Modifier,
                shape = shape,
                contentBehindColor = contentBehindColor,
                draggableToRevelState = draggableToRevelState,
                contentBehind = contentBehind
            )

            FrontContainer(
                draggableToRevelState = draggableToRevelState,
                shape = shape,
                modifier = Modifier,
                content = content
            )
        },
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        layout(width = placeables[1].width, height = placeables[1].height) {
            placeables.forEach {
                it.place(0, 0)
            }
        }
    }
}

@Preview
@Composable
private fun DraggableToRevelPreview() {
    Column {
        val draggableToRevelState = rememberDraggableToRevelState(offsetX = 550f)
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
                //color = Color.Transparent,
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
        Spacer(modifier = Modifier.size(40.dp))
        val draggableToRevelState2 = rememberDraggableToRevelState(offsetX = 0f)
        Box(modifier = Modifier.fillMaxWidth(1f)) {
            DraggableToRevel(
                shape = RoundedCornerShape(5.dp),
                draggableToRevelState = draggableToRevelState2,
                contentBehindColor = MaterialTheme.colorScheme.secondary,
                contentBehind = {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f),
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
                                draggableToRevelState2.toggle()
                            }
                        },
                    color = MaterialTheme.colorScheme.primary,
                    //color = Color.Transparent,
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
}
