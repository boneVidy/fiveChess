package com.example.fivechess

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


open class GameActivity : AppCompatActivity() {
    private var view: GameView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun init() {
        val layout = findViewById<View>(R.id.ll_canver) as LinearLayout
        view = GameView(this)
        view!!.minimumHeight = 500
        view!!.minimumWidth = 300
        view!!.invalidate()
        layout.addView(view)
    }

}