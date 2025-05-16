package io.bibuti.bingo

sealed interface UserEvents {
    data object Play : UserEvents
    data object Pause : UserEvents
    data object Reset : UserEvents
}