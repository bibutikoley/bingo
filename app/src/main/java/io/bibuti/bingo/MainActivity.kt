package io.bibuti.bingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import io.bibuti.bingo.screens.HomeScreen
import io.bibuti.bingo.ui.theme.BingoNumberCallerTheme

class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoNumberCallerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    val uiState by appViewModel.uiState.collectAsStateWithLifecycle()
                    HomeScreen(uiState = uiState) {
                        appViewModel.processEvents(it)
                    }
                }
            }
        }
    }
}