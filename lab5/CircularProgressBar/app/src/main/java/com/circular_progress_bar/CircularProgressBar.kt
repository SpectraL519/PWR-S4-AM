package com.circular_progress_bar

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat.getColor
import kotlin.math.roundToInt


class CircularProgressBarView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    // graphics elements
    var primaryColor: Int = getColor(context, R.color.teal_700)
        get() = field
        set (value: Int) {
            field = value
            invalidate()
        }

    var secondaryColor: Int = getColor(context, R.color.teal_200)
        get() = field
        set (value: Int) {
            field = value
            invalidate()
        }

    var primaryAlpha: Int = 255
        get() = field
        set (value: Int) {
            if (value in 0..256) {
                field = value
                invalidate()
            }
        }

    var progressTextColor: Int = getColor(context, R.color.black)
        get() = field
        set (value: Int) {
            field = value
            invalidate()
        }

    var progressTextSize: Float = 100f
        get() = field
        set (value: Float) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }

    var circleRadius: Float = 250f
        get() = field
        set (value: Float) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }

    // progress data
    var startValue: Float = 0f
        get() = field
        set (value: Float) {
            if (value < this.endValue) {
                field = value
                invalidate()
            }
        }

    var endValue: Float = 100f
        get() = field
        set (value: Float) {
            if (value > this.startValue) {
                field = value
                invalidate()
            }
        }

    private val range: Float
        private get() = this.endValue - this.startValue

    var progress: Float = (this.startValue + this.range / 2)
        get() = field
        set (value: Float) {
            if (value in this.startValue..this.endValue) {
                field = value
                invalidate()
            }
        }

    init {
        this.setBackgroundColor(Color.TRANSPARENT)
        if (attrs != null) {
            val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBarView)

            this.primaryColor = styledAttrs.getColor(
                R.styleable.CircularProgressBarView_primary_color,
                getColor(context, R.color.teal_700)
            )
            this.secondaryColor = styledAttrs.getColor(
                R.styleable.CircularProgressBarView_secondary_color,
                getColor(context, R.color.teal_200)
            )
            this.primaryAlpha = styledAttrs.getInt(R.styleable.CircularProgressBarView_primary_alpha, 255)
            this.progressTextColor = styledAttrs.getColor(
                R.styleable.CircularProgressBarView_progress_text_color,
                getColor(context, R.color.black)
            )
            this.progressTextSize = styledAttrs.getFloat(
                R.styleable.CircularProgressBarView_progress_text_size, 100f
            )
            this.circleRadius = styledAttrs.getFloat(
                R.styleable.CircularProgressBarView_radius, 250f
            )

            this.startValue = styledAttrs.getFloat(
                R.styleable.CircularProgressBarView_start_value, 0f
            )
            this.endValue = styledAttrs.getFloat(
                R.styleable.CircularProgressBarView_end_value, 100f
            )
            this.progress = styledAttrs.getFloat(
                R.styleable.CircularProgressBarView_progress_value, this.startValue
            )
        }
    }

    fun animateProgress (value: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", value)
        objectAnimator.duration = 1500
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null)
            return

        val centerX = width / 2f
        val centerY = height / 2f
        val progressBarStrokeWidth = this.circleRadius / 10f

        // inner circle
        canvas.drawCircle(
            centerX,
            centerY,
            this.circleRadius,
            Paint().apply {
                color = primaryColor
                alpha = primaryAlpha
            }
        )

        // outer circle
        canvas.drawCircle(
            centerX,
            centerY,
            this.circleRadius,
            Paint().apply {
                color = Color.LTGRAY
                style = Paint.Style.STROKE
                strokeWidth = progressBarStrokeWidth
            }
        )

        // progress bar - arc
        canvas.drawArc(
            centerX - this.circleRadius,
            centerY - this.circleRadius,
            centerX + this.circleRadius,
            centerY + this.circleRadius,
            -90f,
            (360f * (this.progress - this.startValue) / this.range),
            false,
            Paint().apply {
                color = secondaryColor
                style = Paint.Style.STROKE
                strokeWidth = progressBarStrokeWidth
            }
        )

        // TODO: progress bar - lines

        // progress text
        val textPaint = Paint().apply {
            color = progressTextColor
            textAlign = Paint.Align.CENTER
            textSize = progressTextSize
        }
        canvas.drawText(
            ((this.progress * 100).roundToInt() / 100.0).toString(),
            centerX,
            (centerY - (textPaint.descent() + textPaint.ascent()) / 2),
            textPaint
        )
    }
}