package io.bibuti.bingo

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

val GENERATE_AFTER = 4.seconds

class AppViewModel(application: Application) : AndroidViewModel(application = application) {

    private val _uiState: MutableStateFlow<BingoViewState> =
        MutableStateFlow(value = BingoViewState.initialState)
    val uiState = _uiState.asStateFlow()

    private var gameJob: Job? = null
    private var tts: TextToSpeech

    init {
        tts = TextToSpeech(application) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.UK
            }
        }
    }

    fun processEvents(userEvents: UserEvents) {
        when (userEvents) {
            UserEvents.Pause -> {
                pauseGame()
            }

            UserEvents.Play -> {
                startGame()
            }

            UserEvents.Reset -> {
                resetGame()
            }
        }
    }

    private fun startGame() {
        if (gameJob?.isActive == true) return // Game already running

        _uiState.update {
            it.copy(currentButtonState = ButtonState.Pause)
        }

        gameJob = viewModelScope.launch {
            while (true) {
                val availableNumbers = _uiState.value.bingoNumbers.filter { !it.isChecked }
                if (availableNumbers.isEmpty()) {
                    // All numbers drawn, game over
                    pauseGame() // or a new state like GameOver
                    break
                }

                val randomIndex = Random.nextInt(availableNumbers.size)
                val selectedBingoItem = availableNumbers[randomIndex]

                _uiState.update { currentState ->
                    val updatedBingoNumbers = currentState.bingoNumbers.map {
                        if (it.number == selectedBingoItem.number) {
                            it.copy(isChecked = true)
                        } else {
                            it
                        }
                    }
                    currentState.copy(
                        currentGeneratedItem = selectedBingoItem,
                        bingoNumbers = updatedBingoNumbers,
                        drawnNumbers = currentState.drawnNumbers + selectedBingoItem
                    )
                }
                _uiState.value.currentGeneratedItem?.let { item ->
                    tts.speak(
                        "${item.number}, ${item.lingo}",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "bingo_number"
                    )
                }
                delay(GENERATE_AFTER)
            }
        }
    }

    private fun pauseGame() {
        gameJob?.cancel()
        _uiState.update {
            it.copy(currentButtonState = ButtonState.Play)
        }
    }

    private fun resetGame() {
        gameJob?.cancel()
        _uiState.update {
            BingoViewState.initialState
        }
        tts.speak(
            "Game Reset",
            TextToSpeech.QUEUE_FLUSH,
            null,
            "bingo_number"
        )
    }

    override fun onCleared() {
        tts.stop()
        tts.shutdown()
        super.onCleared()
    }
}
