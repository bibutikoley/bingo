package io.bibuti.bingo

data class BingoViewState(
    val currentButtonState: ButtonState,
) {
    companion object {
        val initialState = BingoViewState(
            currentButtonState = ButtonState.Play
        )
    }
}