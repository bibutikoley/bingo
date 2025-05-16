package io.bibuti.bingo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.bibuti.bingo.R
import io.bibuti.bingo.components.CurrentGeneratedNumber
import io.bibuti.bingo.components.GlassmorphicCard
import io.bibuti.bingo.components.InteractionButtons
import io.bibuti.bingo.components.NumberGridCard

const val TAG = "HomeScreen"

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBackground()
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentGeneratedNumber(number = 90)
                InteractionButtons()
            }
            GlassmorphicCard(
                modifier = Modifier
                    .size(LocalWindowInfo.current.containerSize.width.div(3).dp)
                    .padding(16.dp)
            ) {
                NumberGridCard(
                    modifier = Modifier.fillMaxSize()
                )
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