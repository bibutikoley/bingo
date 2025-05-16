package io.bibuti.bingo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<BingoViewState> =
        MutableStateFlow(value = BingoViewState.initialState)
    val uiState = _uiState.asStateFlow()

    fun processEvents(userEvents: UserEvents) {
        when (userEvents) {
            UserEvents.Pause -> _uiState.update {
                it.copy(currentButtonState = ButtonState.Play)
            }

            UserEvents.Play -> _uiState.update {
                it.copy(currentButtonState = ButtonState.Pause)
            }

            UserEvents.Reset -> _uiState.update {
                it.copy(currentButtonState = ButtonState.Play)
            }
        }
    }
}
