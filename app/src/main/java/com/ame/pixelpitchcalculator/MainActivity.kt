package com.ame.pixelpitchcalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.sqrt

class MainActivity : Activity() {
    private val screenWidth by lazy { windowManager.defaultDisplay.width }
    private val screenHeight by lazy { windowManager.defaultDisplay.height }

    private val screenInch by lazy { calcScreenSize() }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = LinearLayout(this)
        view.orientation = LinearLayout.VERTICAL
        view.gravity = android.view.Gravity.CENTER

        // Screen inch
        val tvSizeTitle = TextView(this)
        tvSizeTitle.text = getString(R.string.screen_size_inch_title)
        tvSizeTitle.gravity = android.view.Gravity.CENTER
        val sizeResult = TextView(this)
        sizeResult.text = String.format("%.2f", screenInch)
        sizeResult.gravity = android.view.Gravity.CENTER

        // Screen resolution
        val tvResolutionTitle = TextView(this)
        tvResolutionTitle.text = getString(R.string.screen_resolution_title)
        tvResolutionTitle.gravity = android.view.Gravity.CENTER
        val resolutionResult = TextView(this)
        resolutionResult.text = String.format("%d x %d", screenWidth, screenHeight)
        resolutionResult.gravity = android.view.Gravity.CENTER

        // Pixel pitch
        val tvPixelPitchTitle = TextView(this)
        tvPixelPitchTitle.text = getString(R.string.result_title)
        tvPixelPitchTitle.gravity = android.view.Gravity.CENTER
        val pixelPitchResult = TextView(this)
        pixelPitchResult.textSize = 30f
        pixelPitchResult.text = String.format("%.2f", calcPixelPitch())
        pixelPitchResult.gravity = android.view.Gravity.CENTER

        view.addView(tvSizeTitle)
        view.addView(sizeResult)
        view.addView(tvResolutionTitle)
        view.addView(resolutionResult)
        view.addView(tvPixelPitchTitle)
        view.addView(pixelPitchResult)

        setContentView(view)
    }

    /**
     * Calculate screen size in inch
     * @return screen size in inch
     */
    private fun calcScreenSize(): Double {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display?.getRealSize(point)
        } else {
            windowManager.defaultDisplay.getSize(point)
        }
        val w: Double = (point.x / metrics.xdpi).toDouble() // Unit is inch
        val h: Double = (point.y / metrics.ydpi).toDouble() // Unit is inch
        val size = sqrt(w * w + h * h)
        return size
    }

    /**
     * Calculate pixel pitch
     * @return pixel pitch
     */
    private fun calcPixelPitch(): Double =
        ((screenInch * 2.54 * 10) / (sqrt((screenWidth * screenWidth + screenHeight * screenHeight).toDouble()))) * 1000
}
