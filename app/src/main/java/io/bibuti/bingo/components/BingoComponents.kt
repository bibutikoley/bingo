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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
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
            containerColor = Color.White.copy(alpha = .85f)
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

    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {
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