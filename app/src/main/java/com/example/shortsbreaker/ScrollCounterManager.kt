package com.example.shortsbreaker

import android.os.Handler
import android.os.Looper
import android.util.Log

class ScrollCounterManager(
    private val threshold: Int = 5,
    private val timeoutMillis: Long = 10_000L,
    private val onThresholdReached: () -> Unit
) {
    private val TAG = "ScrollCounterManager"

    private var scrollCount = 0
    private val handler = Handler(Looper.getMainLooper())
    private val resetRunnable = Runnable {
        Log.d(TAG, "🕓 Timeout reached, resetting counter.")
        scrollCount = 0
    }

    fun onScrollDetected() {
        scrollCount++
        Log.d(TAG, "⬆️ Scroll count: $scrollCount")

        handler.removeCallbacks(resetRunnable)

        if (scrollCount >= threshold) {
            Log.d(TAG, "✅ Threshold reached ($threshold)")
            scrollCount = 0
            onThresholdReached()
        } else {
            handler.postDelayed(resetRunnable, timeoutMillis)
        }
    }

    fun reset() {
        scrollCount = 0
        handler.removeCallbacks(resetRunnable)
    }
}
