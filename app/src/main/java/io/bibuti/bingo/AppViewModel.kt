package io.bibuti.bingo

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

val GENERATE_AFTER = 4800.milliseconds

class AppViewModel(application: Application) : AndroidViewModel(application = application) {

    private val _uiState: MutableStateFlow<BingoViewState> =
        MutableStateFlow(value = BingoViewState.initialState)
    val uiState = _uiState.asStateFlow()

    private var gameJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

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

    private fun playSoundByNumber(number: Int, onCompletion: (() -> Unit)? = null) {
        val fileName = "${number}.wav" // Assuming files are named like 1.mp3, 2.mp3, etc.
        try {
            getApplication<Application>().assets.openFd(fileName).use { afd ->
                mediaPlayer?.release() // Release previous instance if any
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    prepareAsync() // Prepare asynchronously to not block the UI thread
                    setOnPreparedListener {
                        start()
                    }
                    setOnCompletionListener { mp ->
                        mp.reset() // Reset to reuse or release if not needed soon
                        onCompletion?.invoke()
                    }
                    setOnErrorListener { mp, _, _ ->
                        // Handle errors, e.g., file not found, decoding error
                        println("MediaPlayer Error for $fileName")
                        mp.reset()
                        true // Error handled
                    }
                }
            }
        } catch (e: Exception) {
            println("Error playing sound $fileName: ${e.message}")
            // Fallback or error handling if file doesn't exist or other issue
            mediaPlayer?.release()
            mediaPlayer = null
            onCompletion?.invoke() // Ensure game loop continues if sound fails
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
                    pauseGame()
                    // Optionally play a "game over" sound
                    // playSoundByName("game_over.mp3")
                    break
                }

                val selectedBingoItem = availableNumbers
                    .shuffled()
                    .shuffled()
                    .shuffled()
                    .random()

                var soundPlayed = false
                var soundPlaybackCompleted = false

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

                // Play sound for the current item
                _uiState.value.currentGeneratedItem?.let { item ->
                    playSoundByNumber(item.number) {
                        soundPlaybackCompleted = true
                    }
                    soundPlayed = true
                }

                // Wait for sound to finish or GENERATE_AFTER duration, whichever is longer or just delay
                // Simple delay for now, assuming sounds are shorter than GENERATE_AFTER
                if (soundPlayed) {
                    // A more robust solution might involve waiting for onCompletion if sounds can be long
                    while (!soundPlaybackCompleted) {
                        delay(GENERATE_AFTER) // Check every 100ms if sound completed
                        // Add a timeout here to prevent infinite loop if sound fails to complete
                    }
                } else {
                    delay(GENERATE_AFTER) // If no sound to play, just delay
                }
                // If sounds are guaranteed to be short, we might just use: delay(GENERATE_AFTER)
            }
        }
    }

    private fun pauseGame() {
        gameJob?.cancel()
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            // Do not reset or release here if you want to resume on play
        }
        _uiState.update {
            it.copy(currentButtonState = ButtonState.Play)
        }
    }

    private fun resetGame() {
        gameJob?.cancel()
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            reset() // Reset to release resources and prepare for next potential use
        }
        _uiState.update {
            BingoViewState.initialState
        }
        // Optionally play a "reset" sound
        // playSoundByName("game_reset.mp3")
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
        gameJob?.cancel() // Ensure job is cancelled
    }
}
