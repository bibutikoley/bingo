package io.bibuti.bingo

sealed class ButtonState {
    data object Play : ButtonState()
    data object Pause : ButtonState()
}