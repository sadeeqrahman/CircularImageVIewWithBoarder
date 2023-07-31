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
    private val rect = RectF()
    private val borderPaint = Paint()

    private var borderColor = Color.RED
    private var borderWidth = 5f

    init {
        // Retrieve custom attributes from XML
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
            typedArray.recycle()
        }

        borderPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val radius = width.coerceAtMost(height) / 2f
            path.reset()
            rect.set(0f, 0f, width.toFloat(), height.toFloat())
            path.addCircle(width / 2f, height / 2f, radius, Path.Direction.CW)
            it.clipPath(path)
            super.onDraw(it)

            // Draw the border
            it.drawCircle(
                width / 2f, height / 2f,
                radius - borderPaint.strokeWidth / 2, borderPaint
            )
        }
    }
}
