package com.joasvpereira.lib.compose.views.ui.future.beta
/*

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.reflect.KProperty

@Composable
fun rememberSideMenuScaffoldState(
    isMenuOpen: Boolean = false,
    menuActionSize: Dp? = null,
    position: SideMenuMenuPosition = SideMenuMenuPosition.ON_LEFT,
): SideMenuScaffoldState {
    val currentLocalDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    val sizes = provideScreenDimensions(configuration)
    val (heigth, width) = sizes

    return remember {
        SideMenuScaffoldState(
            screenWidth = width,
            screenHeight = heigth,
            menuActionWidthInit = menuActionSize ?: width,
            isMenuOpenInit = isMenuOpen,
            position = position,
        )
    }
}

fun provideScreenDimensions(configuration: Configuration): Pair<Dp, Dp> {
    return Pair(
        configuration.screenHeightDp.dp,
        configuration.screenWidthDp.dp,
    )
}

enum class SideMenuMenuPosition {
    ON_LEFT,
    ON_RIGHT,
}

class SideMenuScaffoldState(
    val screenWidth: Dp,
    val screenHeight: Dp,
    menuActionWidthInit: Dp,
    isMenuOpenInit: Boolean,
    val position: SideMenuMenuPosition = SideMenuMenuPosition.ON_LEFT,
) {

    var isMenuOpen: MutableState<Boolean> = mutableStateOf(isMenuOpenInit)
    private val menuActionWidth: MutableState<Dp> = mutableStateOf(menuActionWidthInit)

    val currentMenuWidth: Dp
        get() = screenWidth.minus(menuActionWidth.value)

    fun provideMenuOffSet(): Dp = if (isMenuOpen.value) {
        Log.d("JVP", "menuWidthOpen")
        screenWidth.minus(menuActionWidth.value)
    } else {
        Log.d("JVP", "menuWidthClose")
        0.dp
    }

    fun toggleMenu() {
        isMenuOpen.value = !isMenuOpen.value
    }

    fun updateMenuWidth(func: () -> Dp) {
        val widthInDp = func()
        Log.d("JVP", "updateMenuWidth {$widthInDp}")
        menuActionWidth.value = widthInDp
    }

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): SideMenuScaffoldState = this
}

@Composable
fun SideMenuScaffold(
    sideMenuScaffoldState: SideMenuScaffoldState = rememberSideMenuScaffoldState(),
    menuContent: @Composable (scope: BoxScope) -> Unit = {},
    menuAction: @Composable (isMenuOpen: Boolean) -> Unit = { MenuActionDefault(false) },
    menuBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    content: @Composable () -> Unit,
) {
    Log.d("JVP", sideMenuScaffoldState.toString())
    val currentLocalDensity = LocalDensity.current
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            content()
        }

        val menuOffset: Dp by animateDpAsState(targetValue = sideMenuScaffoldState.provideMenuOffSet())
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(sideMenuScaffoldState.screenWidth)
                .offset {
                    IntOffset(
                        x = menuOffset
                            .times(if (sideMenuScaffoldState.position == SideMenuMenuPosition.ON_LEFT) -1 else 1)
                            .roundToPx(),
                        y = 0,
                    ).also {
                        Log.d("JVP", "offset = $it")
                    }
                },
        ) {
            Row {
                if (sideMenuScaffoldState.position == SideMenuMenuPosition.ON_LEFT) {
                    MenuSurfaceContent(sideMenuScaffoldState.currentMenuWidth, menuBackgroundColor, menuContent)
                    ToggleMenuAction(sideMenuScaffoldState, currentLocalDensity, menuBackgroundColor, menuAction)
                } else {
                    ToggleMenuAction(sideMenuScaffoldState, currentLocalDensity, menuBackgroundColor, menuAction)
                    MenuSurfaceContent(sideMenuScaffoldState.currentMenuWidth, menuBackgroundColor, menuContent)
                }
            }
        }
    }
}

@Composable
private fun ToggleMenuAction(
    sideMenuScaffoldState: SideMenuScaffoldState,
    currentLocalDensity: Density,
    menuBackgroundColor: Color,
    menuAction: @Composable (isMenuOpen: Boolean) -> Unit,
) {
    val (alignment, shape) = if (sideMenuScaffoldState.position == SideMenuMenuPosition.ON_LEFT) {
        Pair(
            Alignment.BottomStart,
            RoundedCornerShape(
                topStartPercent = 0,
                topEndPercent = 100,
                bottomStartPercent = 0,
                bottomEndPercent = 100,
            ),
        )
    } else {
        Pair(
            Alignment.BottomEnd,
            RoundedCornerShape(
                topStartPercent = 100,
                topEndPercent = 0,
                bottomStartPercent = 100,
                bottomEndPercent = 0,
            ),
        )
    }

    // val shape =

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 50.dp),
        contentAlignment = alignment,
    ) {
        Surface(
            modifier = Modifier
                .shadow(
                    if (!sideMenuScaffoldState.isMenuOpen.value) 0.dp else 4.dp,
                    shape = shape,
                    clip = true,
                )
                .clip(
                    shape = shape,
                )
                .clickable {
                    Log.d("JVP", "action clicked")
                    sideMenuScaffoldState.toggleMenu()
                    Log.d("JVP", "inline check $sideMenuScaffoldState")
                }
                .onGloballyPositioned { coordinates ->
                    sideMenuScaffoldState.updateMenuWidth {
                        with(currentLocalDensity) {
                            coordinates.size.width.toDp()
                        }
                    }
                    Log.d("JVP2", "menuActionSize =${sideMenuScaffoldState.currentMenuWidth}")
                },
            color = menuBackgroundColor,
        ) {
            menuAction(sideMenuScaffoldState.isMenuOpen.value)
        }
    }
}

@Composable
private fun MenuSurfaceContent(
    width: Dp,
    menuBackgroundColor: Color,
    menuContent: @Composable (scope: BoxScope) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(width = width)
            .shadow(4.dp),
        color = menuBackgroundColor,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            menuContent(this)
        }
    }
}

@Composable
fun MenuActionDefault(isMenuOpen: Boolean) {
    Box(modifier = Modifier.padding(8.dp)) {
        if (!isMenuOpen) {
            Text(text = "MENU", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        } else {
            Text(text = "X", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun SideMenuScaffoldPreview() {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
        }
    }
}
*/
