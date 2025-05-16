package io.bibuti.bingo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.bibuti.bingo.R
import io.bibuti.bingo.components.GlassmorphicCard

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBackground()
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            GlassmorphicCard(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(16.dp)
            ) {

            }
        }
    }
}

@Composable
fun AppBackground() {
    Image(
        painter = painterResource(R.drawable.bg_pencil_bunch),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}