package com.example.game_app.presenters

import com.example.game_app.R
import com.example.game_app.interfaces.IMainActivity

class MainActivityPresenter(private var activity: IMainActivity) {
    fun canvasViewOnClick() {
        activity.makeSound(R.raw.sound)

        if (!activity.canvasView.IsBallInSuccessZone())
            activity.finishGame()
        else
            activity.canvasView.updateGame()
    }

    fun windowViewOnClick() {
        activity.canvasView.performClick()
    }
}