package com.example.game_app.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.game_app.R
import com.example.game_app.canvasClassses.CircleTrajectory
import com.example.game_app.canvasClassses.MovingBall
import com.example.game_app.canvasClassses.SuccessfulZone
import kotlin.random.Random

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
    ) : View(context, attrs) {
    private var ball = MovingBall(1.6, 1.0)
    var difficultCoef = 1
    var score = 0
    var successZone = SuccessfulZone(difficultCoef)

    // this function redraw each screen
    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        var width = getWidth()
        var height = getHeight()

        var textPaint = Paint()
        textPaint.setColor(R.color.black)
        textPaint.setTextSize(200f)
        textPaint.setTextAlign(Paint.Align.CENTER)

        // we need to set the circle radius into the center of the canvas
        // so we set the offset of draw
        CircleTrajectory.drawTrajectory(canvas, width / 2, height / 2)
        ball.drawBall(canvas, width / 2, height / 2)
        ball.increaseAngle()
        successZone.drawZone(canvas, width / 2, height / 2)
        canvas.drawText(score.toString(), (width / 2).toFloat(), (height / 2 + 50).toFloat(), textPaint)
    }

    fun IsBallInSuccessZone(): Boolean {
        var ballAngle = ball.getAngle() % 360
        var zoneStartAngle = successZone.getStartAngle()
        var zoneEndAngle = zoneStartAngle + successZone.getSweepAngle()

        if (ball.direction == MovingBall.Direction.BY_CLOCK &&
            (ballAngle in zoneStartAngle..zoneEndAngle ||
            // without this check the app won't work within 270-360 degrees by clock
             (360 + ballAngle in zoneStartAngle..zoneEndAngle && ballAngle < 0))
        ) {
            return true
        }

        // angle always < 0
        if (ball.direction == MovingBall.Direction.AGAINST_CLOCK &&
            (360 + ballAngle) in zoneStartAngle..zoneEndAngle) {
            return true
        }

        return false
    }

    // create new successful zone
    // create new ball obj
    fun updateGame() {
        difficultCoef *= 2
        successZone = SuccessfulZone(difficultCoef)
        var direction: MovingBall.Direction

        if (ball.direction == MovingBall.Direction.AGAINST_CLOCK)
            direction = MovingBall.Direction.BY_CLOCK
        else
            direction = MovingBall.Direction.AGAINST_CLOCK

        ball = MovingBall(1.6, 1.0)
        ball.direction = direction

        score++
    }
}