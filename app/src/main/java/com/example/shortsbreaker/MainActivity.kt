package com.example.shortsbreaker

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔧 btnEnableService ID를 기준으로 버튼 클릭 리스너 설정
        findViewById<Button>(R.id.btnEnableService).setOnClickListener {
            // 접근성 설정 화면으로 이동
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }
}
