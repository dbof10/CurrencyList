package com.ctech.common.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap.Config.ARGB_8888
import android.os.Looper
import android.text.TextPaint
import android.widget.ImageView
import java.lang.NullPointerException
import java.util.*

/**
 * https://github.com/adwardstark/materialtextdrawable-for-android/blob/master/mtextdrawable/src/main/java/com/adwardstark/mtextdrawable/MaterialTextDrawable.kt
 */

class MaterialTextDrawable private constructor(builder: Builder) {

    companion object {
        private const val MaterialDark = 400
        private const val MaterialMedium = 700
        private const val MaterialLight = 900

        fun with(context: Context): Builder = Builder().with(context)
        private fun isOnMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()
    }

    enum class MaterialShape {
        CIRCLE,
        RECTANGLE
    }

    enum class MaterialColorMode {
        LIGHT,
        MEDIUM,
        DARK
    }

    private var context: Context
    private var size: Int
    private var colorMode: MaterialColorMode
    private var drawableShape: MaterialShape
    private var text: String

    init {
        this.context = builder.context
        this.size = builder.size
        this.colorMode = builder.colorMode
        this.drawableShape = builder.drawableShape
        this.text = builder.text
    }

    class Builder {

        internal lateinit var context: Context
        internal var size = 150
        internal var colorMode: MaterialColorMode = MaterialColorMode.MEDIUM
        internal var drawableShape: MaterialShape = MaterialShape.CIRCLE
        internal var text: String = ""

        fun with(context: Context): Builder {
            this.context = context
            return this
        }

        fun textSize(size: Int): Builder {
            this.size = size
            return this
        }

        fun shape(shape: MaterialShape): Builder {
            this.drawableShape = shape
            return this
        }

        fun colorMode(mode: MaterialColorMode): Builder {
            this.colorMode = mode
            return this
        }

        fun text(text: String): Builder {
            this.text = text
            return this
        }

        fun get(): BitmapDrawable {
            if(text == ""){
                throw NullPointerException("No text provided, " +
                        "call text(<your_text>) before calling this method")
            }
            return MaterialTextDrawable(this).getTextDrawable()
        }

        fun into(view: ImageView) {
            if(!isOnMainThread()) {
                throw IllegalArgumentException("You must call this method on the main thread")
            }
            // Set text-drawable
            view.setImageDrawable(get())
        }

        fun into(view: ImageView, scale: ImageView.ScaleType) {
            view.scaleType = scale
            into(view)
        }

    }

    private fun getTextDrawable(): BitmapDrawable {
        val initials = if(text.length > 1){
            getFirstChar(text)
        } else {
            text
        }
        val textPaint = textPainter(calculateTextSize(this.size))
        val painter = Paint()
        painter.isAntiAlias = true

        if(drawableShape == MaterialShape.RECTANGLE) {
            painter.color = ColorGenerator(getColorMode(colorMode)).getRandomColor()
        } else {
            painter.color = Color.TRANSPARENT
        }

        val areaRectangle = Rect(0, 0, size, size)
        val bitmap = Bitmap.createBitmap(size, size, ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRect(areaRectangle, painter)

        if(drawableShape == MaterialShape.RECTANGLE) {
            painter.color = Color.TRANSPARENT
        } else {
            painter.color = ColorGenerator(getColorMode(colorMode)).getRandomColor()
        }

        val bounds = RectF(areaRectangle)
        bounds.right = textPaint.measureText(initials, 0, 1)
        bounds.bottom = textPaint.descent() - textPaint.ascent()

        bounds.left += (areaRectangle.width() - bounds.right) / 2.0f
        bounds.top += (areaRectangle.height() - bounds.bottom) / 2.0f

        canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, painter)
        canvas.drawText(initials, bounds.left, bounds.top - textPaint.ascent(), textPaint)
        return BitmapDrawable(context.resources, bitmap)
    }

    private fun calculateTextSize(size: Int): Float {
        return (size / 4.125).toFloat()
    }

    private fun getFirstChar(text: String): String {
        return text.first().toString().toUpperCase(Locale.ROOT)
    }

    private fun textPainter(size: Float): TextPaint {
        val textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = size * context.resources.displayMetrics.scaledDensity
        textPaint.color = Color.WHITE
        return textPaint
    }

    private fun getColorMode(mode: MaterialColorMode): Int {
        return when(mode) {
            MaterialColorMode.LIGHT -> {
                MaterialLight
            }
            MaterialColorMode.MEDIUM -> {
                MaterialMedium
            }
            MaterialColorMode.DARK -> {
                MaterialDark
            }
        }
    }

    internal class ColorGenerator(colorMode: Int = 700) {

        private val randomiser: Stack<Int> = Stack()
        private val colors: Stack<Int> = Stack()

        init {
            if (colorMode==700){
                randomiser.addAll(
                    //A 700
                    listOf(
                        -0xd32f2f, -0xC2185B, -0x7B1FA2, -0x512DA8,
                        -0x303F9F, -0x1976D2, -0x0288D1, -0x0097A7,
                        -0x00796B, -0x388E3C, -0x689F38, -0xAFB42B,
                        -0xFBC02D, -0xFFA000, -0xF57C00,  -0xE64A19,
                        -0x5D4037, -0x616161, -0x455A64
                    )
                )
            }

            //A400
            if(colorMode==400){
                randomiser.addAll(
                    listOf(
                        -0xef5350, -0xEC407A, -0xAB47BC, -0x7E57C2,
                        -0x5C6BC0, -0x42A5F5, -0x29B6F6, -0x26C6DA,
                        -0x26A69A, -0x66BB6A, -0x9CCC65, -0xD4E157,
                        -0xFFEE58, -0xFFCA28, -0xFFA726, -0xFF7043,
                        -0x8D6E63, -0xBDBDBD, -0x78909C
                    )
                )
            }

            //A900
            if(colorMode==900){
                randomiser.addAll(
                    listOf(
                        -0xb71c1c, -0x880E4F, -0x4A148C, -0x311B92,
                        -0x1A237E, -0x0D47A1, -0x01579B, -0x006064,
                        -0x004D40, -0x1B5E20, -0x33691E, -0x827717,
                        -0xF57F17, -0xFF6F00, -0xE65100, -0xBF360C,
                        -0x3E2723, -0x212121, -0x263238
                    )
                )
            }

        }

        fun getRandomColor(): Int {
            if(colors.size == 0) {
                while (!randomiser.isEmpty()) colors.push(randomiser.pop())
                Collections.shuffle(colors)
            }
            val color: Int = colors.pop()
            randomiser.push(color)
            return color
        }

    }
}