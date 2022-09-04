package com.example.game_app.canvasClassses

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import com.example.game_app.R
import java.lang.IllegalArgumentException

// ball that moving in game
// tick is the time when ball change his angle (position)
@SuppressLint("ResourceAsColor")
class MovingBall(private var maxAnglePerTick: Double = .1, private var minAnglePerTick: Double = 0.01) {

    enum class Direction {
        AGAINST_CLOCK,
        BY_CLOCK
    }

    companion object {
        const val radius = 30f // in dp
    }

    private var paint: Paint = Paint()
    var direction: Direction = Direction.BY_CLOCK
    private var angle = -90.0 // start angle in degrees

    init {
        if (minAnglePerTick >= maxAnglePerTick) {
            throw IllegalArgumentException("wrong arguments, min angle should be less than max angle")
        }

        val ballColor = R.color.ballColor
        this.paint.setColor(ballColor)
    }


    fun getAngle(): Double {
        return angle
    }

    fun increaseAngle() {
        if (direction == Direction.AGAINST_CLOCK)
            this.angle -= (Math.random() * (maxAnglePerTick - minAnglePerTick) + minAnglePerTick)
        else
            this.angle += (Math.random() * (maxAnglePerTick - minAnglePerTick) + minAnglePerTick)
    }

    fun getMaxAnglePerTick(): Double {
        return maxAnglePerTick
    }

    fun getMinAnglePerTick(): Double {
        return minAnglePerTick
    }

    fun setMaxAnglePerTick(maxAnglePerTick: Double) {
        this.maxAnglePerTick = maxAnglePerTick
    }

    fun setMinAnglePerTick(minAnglePerTick: Double) {
        this.minAnglePerTick = minAnglePerTick
    }

    // offsetX, offsetY need for set the ball into right place
    fun drawBall(canvas: Canvas, offsetX: Int, offsetY: Int) {
        canvas.drawCircle(
            (Math.cos(Math.toRadians(angle)) * CircleTrajectory.radius + offsetX).toFloat(),
            (Math.sin(Math.toRadians(angle)) * CircleTrajectory.radius + offsetY).toFloat(),
            radius,
            this.paint
        )
    }
}