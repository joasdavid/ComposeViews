package com.joasvpereira.lib.compose.undermenuscaffold

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick()
    }
}

class UnderMenuScaffoldState(
    private val direction: SlideDirection = SlideDirection.RIGHT,
    isOpenInit: Boolean = false,
) {
    private var isOpenState: MutableState<Boolean> = mutableStateOf(isOpenInit)

    var isOpen: Boolean
        get() = isOpenState.value
        private set(value) {
            isOpenState.value = value
        }

    var previewBitmap: Bitmap? = null

    fun provideOffset(screenWidth: Int) = when {
        !isOpen -> 0f
        direction == SlideDirection.RIGHT -> screenWidth * .75f
        direction == SlideDirection.LEFT -> screenWidth * .75f * -1
        else -> 0f
    }

    fun provideScale() = if (isOpen) {
        .75f
    } else {
        1f
    }

    fun toggleOpenState(view: View) {
        if (isOpen) {
            closeMenu()
        } else {
            openMenu(view)
        }
    }

    fun closeMenu() {
        isOpen = false
        previewBitmap = null
    }

    fun openMenu(view: View) {
        isOpen = true
        previewBitmap = view.screenShotMe()
    }

    operator fun getValue(nothing: Nothing?, property: KProperty<*>) = this
    fun provideMenuPadding(windowPadding: PaddingValues, totalWidth: Dp): PaddingValues {
        val sidePadding = (totalWidth - (totalWidth * .75f)) / 2
        return if (direction == SlideDirection.LEFT) {
            val startPadding = windowPadding.calculateStartPadding(LayoutDirection.Ltr) + sidePadding
            PaddingValues(
                start = startPadding,
                end = windowPadding.calculateEndPadding(LayoutDirection.Ltr),
                top = windowPadding.calculateTopPadding(),
                bottom = windowPadding.calculateBottomPadding(),
            )
        } else {
            val endPadding = windowPadding.calculateEndPadding(LayoutDirection.Ltr) + sidePadding
            PaddingValues(
                start = windowPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = endPadding,
                top = windowPadding.calculateTopPadding(),
                bottom = windowPadding.calculateBottomPadding(),
            )
        }
    }
}

@Composable
fun rememberUnderMenuScaffold(direction: SlideDirection = SlideDirection.RIGHT): UnderMenuScaffoldState =
    remember {
        UnderMenuScaffoldState(
            direction = direction,
        )
    }

enum class SlideDirection {
    LEFT,
    RIGHT,
}

fun View.screenShotMe(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888,
    )
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderMenuScaffold(
    state: UnderMenuScaffoldState = rememberUnderMenuScaffold(),
    animationDurationMillis: Int = 1500,
    menuContent: @Composable (padding: PaddingValues) -> Unit,
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Scaffold { paddings ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val maxWidthDp = with(LocalDensity.current) { constraints.maxWidth.toDp() }
            Box(modifier = Modifier.fillMaxSize()) { menuContent(state.provideMenuPadding(windowPadding = paddings, totalWidth = maxWidthDp)) }
            mainDisplay(
                state = state,
                animationDurationMillis = animationDurationMillis,
                maxWidth = constraints.maxWidth,
                content = {
                    content(paddings)
                }
            )
        }
    }
}

@Composable
private fun menuDisplay(
    menuContent: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        menuContent()
    }
}

@Composable
private fun mainDisplay(
    state: UnderMenuScaffoldState,
    animationDurationMillis: Int,
    maxWidth: Int,
    content: @Composable () -> Unit,
) {
    val scaAnime by animateFloatAsState(
        targetValue = state.provideScale(),
        animationSpec = TweenSpec(animationDurationMillis),
        label = "float scale",
    )
    val roundedCorner = if (scaAnime == 1f) 0.dp else 30.dp
    val xOffAnime by animateIntAsState(
        targetValue = state.provideOffset(maxWidth).toInt(),
        animationSpec = TweenSpec(animationDurationMillis),
        label = "float offset",
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset {
                IntOffset(xOffAnime, 0)
            }
            .scale(
                scaAnime,
            )
            .clip(RoundedCornerShape(roundedCorner))
            .noRippleClickable {
                if (state.isOpen) {
                    state.closeMenu()
                }
            },
    ) {
        if (state.previewBitmap != null) {
            bitmapDisplay(bitmap = state.previewBitmap!!)
        } else {
            content()
        }
    }
}

@Composable
private fun bitmapDisplay(bitmap: Bitmap) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(painter = BitmapPainter(bitmap.asImageBitmap()), contentDescription = "")
    }
}

@Preview()
@Composable
fun UnderMenuScaffoldPreview() {
    val state = rememberUnderMenuScaffold(direction = SlideDirection.RIGHT)
    UnderMenuScaffold(menuContent = {
        Box(modifier = Modifier.fillMaxSize().background(Color.Green).padding(it).background(Color.Red))
    }, state = state) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "THIS IS SCREEN BODY")
            val view = LocalView.current
            Button(
                onClick = { state.openMenu(view) },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = "OPEN")
            }
        }
    }
}
