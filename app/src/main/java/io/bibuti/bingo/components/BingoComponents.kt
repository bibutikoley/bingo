package io.bibuti.bingo.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ReplayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.Text
import io.bibuti.bingo.BingoItem
import io.bibuti.bingo.ButtonState

@Composable
fun GlassmorphicCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        border = CardDefaults.border(
            focusedBorder = Border.None,
            pressedBorder = Border.None,
            border = Border.None
        ),
        colors = CardDefaults.colors(
            containerColor = Color.LightGray.copy(alpha = .85f)
        ),
        onClick = {},
        modifier = modifier
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberGridCard(
    modifier: Modifier = Modifier,
    numbers: List<BingoItem>
) {
    val columns = 10
    val rows = 9

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        for (row in 0 until rows) {
            Row(modifier = Modifier.weight(1f)) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < numbers.size) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(2.dp)
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .background(
                                    shape = CircleShape,
                                    color = Color.White.copy(
                                        if (numbers[index].isChecked) {
                                            1f
                                        } else {
                                            0.3f
                                        }
                                    )
                                )
                                .border(
                                    width = 2.dp,
                                    shape = CircleShape,
                                    color = Color.Black.copy(
                                        if (numbers[index].isChecked) {
                                            1f
                                        } else {
                                            0.3f
                                        }
                                    )
                                )
                        ) {
                            if (numbers[index].isChecked) {
                                Text(
                                    text = numbers[index].number.toString(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentGeneratedNumber(modifier: Modifier = Modifier, number: Int?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(width = 4.dp, shape = CircleShape, color = Color.Black)
            .size(250.dp)
    ) {
        Text(
            text = number?.toString() ?: "-",
            fontSize = 80.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
    }
}

@Composable
fun InteractionButtons(
    currentButtonState: ButtonState,
    onPlayTapped: (() -> Unit),
    onPauseTapped: (() -> Unit),
    onResetConfirmed: (() -> Unit)
) {
    var resetClickCount by remember { mutableIntStateOf(0) }
    val playFocusRequester = remember { FocusRequester() }
    val pauseFocusRequester = remember { FocusRequester() }

    LaunchedEffect(currentButtonState) {
        when (currentButtonState) {
            ButtonState.Pause -> pauseFocusRequester.requestFocus()
            ButtonState.Play -> playFocusRequester.requestFocus()
        }
    }
    Row {
        if (currentButtonState == ButtonState.Play) {
            IconButton(
                onClick = {
                    resetClickCount = 0
                    onPlayTapped()
                },
                modifier = Modifier
                    .focusRequester(playFocusRequester)
            ) {
                Icon(Icons.Default.PlayCircle, contentDescription = null)
            }
        }
        if (currentButtonState == ButtonState.Pause) {
            IconButton(
                onClick = {
                    resetClickCount = 0
                    onPauseTapped()
                },
                modifier = Modifier
                    .focusRequester(pauseFocusRequester)
            ) {
                Icon(Icons.Default.PauseCircle, contentDescription = null)
            }
        }
        Spacer(Modifier.width(4.dp))
        IconButton(
            onClick = {
                resetClickCount++
                if (resetClickCount == 3) {
                    onResetConfirmed()
                    resetClickCount = 0
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(Icons.Default.ReplayCircleFilled, contentDescription = null)
        }
    }
}

@Composable
fun RecentlyGeneratedNumbers(drawnNumbers: List<BingoItem>) {
    val recentThree = drawnNumbers.takeLast(3).reversed()

    GlassmorphicCard(
        modifier = Modifier
            .wrapContentWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            "Last ${recentThree.size} Number${if (recentThree.size > 1) "s" else ""}: ",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 4.dp),
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(72.dp)
        ) {
            recentThree.forEachIndexed { index, bingoItem ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(56.dp)
                        .background(shape = CircleShape, color = Color.White)
                        .border(width = 2.dp, shape = CircleShape, color = Color.Black)
                ) {
                    Text(
                        text = bingoItem.number.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
            }
        }
    }
}