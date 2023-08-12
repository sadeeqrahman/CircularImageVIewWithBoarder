package com.sadeeq.app.circularimageviewwithboarder

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val path = Path()
    private val borderPaint = Paint()
    private val spacePaint = Paint()

    private var borderColor = Color.RED
    private var borderWidth = 5f
    private var spaceBetweenBorderAndImage = 10f
    private var spaceColor = Color.TRANSPARENT // Default transparent color

    init {
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.CircularImageView, 0, 0
            )
            borderColor = typedArray.getColor(
                R.styleable.CircularImageView_borderColor, Color.RED
            )
            borderWidth = typedArray.getDimension(
                R.styleable.CircularImageView_borderWidth, 5f
            )
            spaceBetweenBorderAndImage = typedArray.getDimension(
                R.styleable.CircularImageView_spaceBetweenBorderAndImage, 10f
            )
            spaceColor = typedArray.getColor(
                R.styleable.CircularImageView_spaceColor, Color.TRANSPARENT
            )
            typedArray.recycle()
        }

        borderPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }

        spacePaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = spaceColor
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = (width - borderWidth) / 2f

            // Draw the space area (filled circle)
            spacePaint.style = Paint.Style.FILL
            it.drawCircle(centerX, centerY, radius - spaceBetweenBorderAndImage, spacePaint)

            // Draw the border
            borderPaint.style = Paint.Style.STROKE
            it.drawCircle(centerX, centerY, radius, borderPaint)

            // Clip the canvas to draw the image inside the border with space
            path.reset()
            path.addCircle(centerX, centerY, radius - spaceBetweenBorderAndImage, Path.Direction.CW)
            it.clipPath(path)
            super.onDraw(it)
        }
    }

}
