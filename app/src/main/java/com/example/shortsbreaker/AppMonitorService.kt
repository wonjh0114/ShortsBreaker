package com.example.shortsbreaker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AppMonitorService : AccessibilityService() {

    private val TAG = "AppMonitorService"
    private val handler = Handler(Looper.getMainLooper())

    private val scrollCounter = ScrollCounterManager(
        threshold = 5,
        timeoutMillis = 10_000L,
        onThresholdReached = { triggerPopup() }
    )

    override fun onServiceConnected() {
        Log.d(TAG, "✅ Accessibility Service Connected")
        serviceInfo = serviceInfo.apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_SCROLLED or AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            packageNames = arrayOf("com.google.android.youtube", "com.instagram.android")
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.source == null) return

        val packageName = event.packageName?.toString() ?: return
        val rootNode = rootInActiveWindow

        val isTargetShorts = when (packageName) {
            "com.google.android.youtube" -> ShortsDetector.isYoutubeShorts(rootNode)
            "com.instagram.android" -> ShortsDetector.isInstagramReels(rootNode)
            else -> false
        }

        if (isTargetShorts && event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            Log.d(TAG, "📱 Scrolled on $packageName (Shorts/Reels)")
            scrollCounter.onScrollDetected()
        }
    }

    override fun onInterrupt() {
        Log.w(TAG, "⚠️ Accessibility Service Interrupted")
    }

    private fun triggerPopup() {
        Log.i(TAG, "🚨 Threshold reached, triggering popup")
        val intent = Intent(this, PopupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }
}
