package com.corbettcode.mybirdapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "My Bird App") {
        App()
    }
}