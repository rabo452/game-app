package com.example.game_app.interfaces

import android.view.View
import com.example.game_app.customViews.CanvasView

interface IMainActivity {
    var canvasView: CanvasView
    var windowView: View

    fun finishGame()
    fun startGame()
    fun createFrame()
    fun makeSound(soundSourceId: Int)
}