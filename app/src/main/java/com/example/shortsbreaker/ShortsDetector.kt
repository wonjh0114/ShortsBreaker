package com.example.shortsbreaker

import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

object ShortsDetector {

    private const val TAG = "ShortsDetector"

    fun isYoutubeShorts(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // 예시: Shorts 화면은 제목 텍스트가 거의 없음
        val titleNodes = root.findAccessibilityNodeInfosByViewId("com.google.android.youtube:id/title")
        val playButton = root.findAccessibilityNodeInfosByText("Shorts")
        val result = titleNodes.isNullOrEmpty() && playButton.isNotEmpty()
        Log.d(TAG, "isYoutubeShorts: $result")
        return result
    }

    fun isInstagramReels(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // 예시: "Reels" 라는 텍스트나 특정 구조가 반복됨
        val reelNodes = root.findAccessibilityNodeInfosByText("Reels")
        val videoNodes = root.findAccessibilityNodeInfosByViewId("com.instagram.android:id/reel_view")
        val result = reelNodes.isNotEmpty() || videoNodes.isNotEmpty()
        Log.d(TAG, "isInstagramReels: $result")
        return result
    }
}
