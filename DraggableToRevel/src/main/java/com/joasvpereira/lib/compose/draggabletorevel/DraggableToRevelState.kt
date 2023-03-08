package com.joasvpereira.lib.compose.draggabletorevel

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import kotlin.math.abs
import kotlin.math.roundToInt

class DraggableToRevelState(
    var offsetXInit: Float = 0f,
    val direction: RevelDirection,
) {

    internal var maxGap by mutableStateOf(0f)
    internal var isDragging: Boolean by mutableStateOf(false)
    private var offsetX = mutableStateOf(offsetXInit)
    private val onDelta: (Float) -> Unit = {
        val finalValue = offsetX.value + it
        offsetX.value = valueBetween(
            currentValue = finalValue,
            max = if (direction == RevelDirection.RIGHT) 0f else maxGap,
            min = if (direction == RevelDirection.RIGHT) 0 - maxGap else 0f,
        )
    }
    private var onDeltaState = mutableStateOf(onDelta)
    var draggableState by mutableStateOf(DraggableState { onDeltaState.value.invoke(it) })
    fun provideIntOffset() = run { IntOffset(offsetX.value.roundToInt(), 0) }

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun endPosition() {
        if (abs(offsetX.value) > maxGap / 3) {
            open()
        } else {
            close()
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun toggle() {
        if (offsetX.value == 0f) {
            open()
        } else {
            close()
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun close() {
        val anim = AnimationState(offsetX.value)
        anim.animateTo(0f) {
            offsetX.value = this.value
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun open() {
        val anim = AnimationState(offsetX.value)
        anim.animateTo(if (direction == RevelDirection.LEFT) maxGap else 0 - maxGap) {
            offsetX.value = this.value
        }
    }

    private fun <T : Number> valueBetween(
        currentValue: T,
        max: T,
        min: T,
    ): T {
        if (currentValue.toDouble() >= min.toDouble() && currentValue.toDouble() <= max.toDouble()) return currentValue
        if (currentValue.toDouble() < min.toDouble()) return min
        return max
    }
}
