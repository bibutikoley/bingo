package io.bibuti.bingo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import io.bibuti.bingo.R

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBackground()
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