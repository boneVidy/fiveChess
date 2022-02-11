package com.example.fivechess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnExit = findViewById<View>(R.id.button2) as Button
        val btnStart = findViewById<View>(R.id.button1) as Button
        val musicSwitch = findViewById<View>(R.id.switch_btn) as SwitchMaterial
        btnExit.setOnClickListener {
            val intent = Intent(baseContext, Music::class.java)
            stopService(intent)
            finish()
        }
        btnStart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        musicSwitch.setOnCheckedChangeListener{_, isChecked ->
            val action:String = if (isChecked) Music.PLAY_ACTION else Music.PAUSE_ACTTION;
            val intent = Intent(this, Music::class.java)
            intent.action = action
            startService(intent)
        }
    }
}