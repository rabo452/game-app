package com.example.game_app.canvasClassses

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import com.example.game_app.R

// circle trajectory by that trajectory ball is moving
@SuppressLint("ResourceAsColor")
class CircleTrajectory {
    companion object {
        const val radius = 400f

        fun drawTrajectory(canvas: Canvas, offsetX: Int, offsetY: Int) {
            val paint = Paint()
            paint.setColor(R.color.circleTrajectoryColor)
            paint.setStyle(Paint.Style.STROKE)
            paint.setStrokeWidth(MovingBall.radius * 2 + 2)

            canvas.drawCircle(offsetX.toFloat(), offsetY.toFloat(), radius, paint)
        }
    }
}