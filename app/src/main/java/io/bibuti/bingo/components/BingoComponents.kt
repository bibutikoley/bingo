package io.bibuti.bingo.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ReplayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun NumberGridCard(modifier: Modifier = Modifier) {
    val numbers = (1..90).toList()
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
                                .padding(4.dp)
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .background(shape = CircleShape, color = Color.White)
                                .border(width = 2.dp, shape = CircleShape, color = Color.Black)
                        ) {
                            Text(
                                text = numbers[index].toString(),
                                fontSize = 18.sp,
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

@Composable
fun CurrentGeneratedNumber(modifier: Modifier = Modifier, number: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(width = 4.dp, shape = CircleShape, color = Color.Black)
            .size(250.dp)
    ) {
        Text(
            text = "$number",
            fontSize = 80.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
    }
}

@Composable
fun InteractionButtons(
    onPlayTapped: (() -> Unit),
    onPauseTapped: (() -> Unit),
    onResetConfirmed: (() -> Unit)
) {
    var resetClickCount by remember { mutableIntStateOf(0) }
    Row {
        IconButton(
            onClick = {
                resetClickCount = 0
                onPlayTapped()
            },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(Icons.Default.PlayCircle, contentDescription = null)
        }
        IconButton(
            onClick = {
                resetClickCount = 0
                onPauseTapped()
            },
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(Icons.Default.PauseCircle, contentDescription = null)
        }
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