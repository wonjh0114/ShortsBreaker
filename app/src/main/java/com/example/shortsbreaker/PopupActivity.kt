package com.example.shortsbreaker

import android.app.Activity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import android.view.WindowManager

class PopupActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        // 🔧 정확한 방식으로 텍스트뷰 접근
        val popupText = findViewById<TextView>(R.id.popup_text)
        popupText?.text = getString(R.string.popup_message)

        Toast.makeText(this, getString(R.string.popup_message), Toast.LENGTH_SHORT).show()

        // 🔧 진동 권한 & 최신 API 방식 사용
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val effect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)

        // 🔧 팝업이 다른 앱 위에 자연스럽게 나타나도록 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        )
    }
}
