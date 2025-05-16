package io.bibuti.bingo.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults

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

@Composable
fun NumberGridCard(modifier: Modifier = Modifier) {

}