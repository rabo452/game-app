package com.example.game_app.canvasClassses

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import com.example.game_app.R
import kotlin.random.Random

// much coef - more difficult
@SuppressLint("ResourceAsColor")
class SuccessfulZone(difficultCoefficient: Int = 1) {
    private val paint = Paint()
    private var startAngle: Double
    private var sweepAngle: Double

    init {
        // generate angles
        val startAngle = Random.nextInt(0, 310).toDouble()
        // after start angle the draw method would draw until startAngle + sweepAngle
        var sweepAngle: Double

        sweepAngle = if (difficultCoefficient < 8)
            Random.nextDouble(30.0, 50.0)
        else
            Random.nextInt(20, 25).toDouble()


        this.startAngle = startAngle
        this.sweepAngle = sweepAngle

        paint.setColor(R.color.succeessfulZoneColor)
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(MovingBall.radius * 2 + 2)
    }

    fun getStartAngle(): Double {
        return this.startAngle
    }

    fun getSweepAngle(): Double {
        return this.sweepAngle
    }

    fun drawZone(canvas: Canvas, offsetX: Int, offsetY: Int) {
        canvas.drawArc(
            offsetX.toFloat() - CircleTrajectory.radius,
            offsetY.toFloat() - CircleTrajectory.radius,
            offsetX.toFloat() + CircleTrajectory.radius,
            offsetY.toFloat() + CircleTrajectory.radius,
            startAngle.toFloat(),
            sweepAngle.toFloat(),
            false,
            paint)
    }
}