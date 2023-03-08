package com.joasvpereira.lib.compose.views.ui.future.beta
/*

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.joasvpereira.dev.mokeupui.compose.screen.organizer.main.SimpleSpace
import com.joasvpereira.main.compose.division.DivisionChart
import com.joasvpereira.main.presentation.icons.DivisionIcons
import pt.joasvpereira.coreui.scaffold.ToolBarConfig
import pt.joasvpereira.coreui.scaffold.ToolbarTitleCentered

@Composable
fun CollapseHeaderScaffold(
    modifier: Modifier = Modifier,
    header: @Composable (headerMaxHeight: Dp) -> Unit,
    body: @Composable () -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        var headerHeight: Dp by remember { mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Box(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                headerHeight = with(localDensity) { coordinates.size.height.toDp() }
            },
        ) {
            header(headerHeight)
        }
        BodyContainer(scroll, headerHeight, body)
    }
}

@Composable
fun BodyContainer(scrollState: ScrollState, headerMaxHeight: Dp, body: @Composable () -> Unit) {
    BoxWithConstraints {
        val boxMaxHeight = this.maxHeight
        val maxHeightDensity = with(LocalDensity.current) { headerMaxHeight.toPx() }.toInt()
        val offset = (maxHeightDensity - scrollState.value).coerceAtLeast(0)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = scrollState)
                .offset {
                    IntOffset(x = 0, y = offset)
                },
        ) {
            val shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
            )
            Box(
                modifier = Modifier
                    .heightIn(min = boxMaxHeight)
                    .shadow(elevation = 4.dp, shape = shape)
                    .clip(shape = shape)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Box(modifier = Modifier.padding(top = 20.dp), content = { body() })
            }
        }
    }
}

@Preview
@Composable
fun CollapseHeaderScaffoldPreview() {
    CollapseHeaderScaffold(
        header = { headerMaxHeight ->
            Column {
                ToolbarTitleCentered(
                    toolBarConfig = ToolBarConfig(title = "divisionName", onLeftIconClick = {}),
                )
                SimpleSpace(size = 20.dp)
                DivisionChart(
                    5,
                    1f,
                    5,
                    .5f,
                    DivisionIcons.desk.resId,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                SimpleSpace(size = 20.dp)
            }
        },
        body = {
            Column {
                for (i in 0..100) {
                    Text(text = "test scroll = $i")
                }
                Text(text = "EOF")
            }
        },
    )
}
*/
