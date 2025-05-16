package io.bibuti.bingo.screens

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import io.bibuti.bingo.BingoItem
import io.bibuti.bingo.BingoViewState
import io.bibuti.bingo.R
import io.bibuti.bingo.UserEvents
import io.bibuti.bingo.components.CurrentGeneratedNumber
import io.bibuti.bingo.components.GlassmorphicCard
import io.bibuti.bingo.components.InteractionButtons
import io.bibuti.bingo.components.NumberGridCard
import io.bibuti.bingo.components.RecentlyGeneratedNumbers

const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    uiState: BingoViewState,
    onUserEvents: (UserEvents) -> Unit,
) {
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
                CurrentGeneratedNumber(number = uiState.currentGeneratedItem?.number)
                InteractionButtons(
                    currentButtonState = uiState.currentButtonState,
                    onPlayTapped = {
                        Log.d(TAG, "HomeScreen: OnPlayTapped")
                        onUserEvents.invoke(UserEvents.Play)
                    },
                    onPauseTapped = {
                        Log.d(TAG, "HomeScreen: OnPauseTapped")
                        onUserEvents.invoke(UserEvents.Pause)
                    },
                    onResetConfirmed = {
                        Log.d(TAG, "HomeScreen: OnResetTapped")
                        onUserEvents.invoke(UserEvents.Reset)
                    }
                )
                RecentlyGeneratedNumbers(drawnNumbers = uiState.drawnNumbers)
                ExtraInfo(uiState.drawnNumbers)
            }
            GlassmorphicCard(
                modifier = Modifier
                    .size(LocalWindowInfo.current.containerSize.width.div(3).dp)
                    .padding(16.dp)
            ) {
                NumberGridCard(
                    modifier = Modifier.fillMaxSize(),
                    numbers = uiState.bingoNumbers
                )
            }
        }
    }
}

@Composable
fun ExtraInfo(drawnNumbers: List<BingoItem>) {
    GlassmorphicCard {
        Text(
            "Drawn Numbers: ${drawnNumbers.size}/90",
            modifier = Modifier.padding(16.dp),
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
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