package com.example.fivechess

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


class Music : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private fun isServiceRunning(context: Context, className: String): Boolean {
            if (className.isNullOrEmpty()) {
                return false

            }

            var isRunning: Boolean = false

            val activityManager: ActivityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            val serviceList = activityManager.getRunningServices(30)

            if (serviceList.isEmpty()) {
                return false

            }

            serviceList.forEach { item ->
                if (item.service.className == className) {
                    isRunning = true
                    return@forEach
                }
            }
            return isRunning
        }

        fun isServiceRunning(context: Context): Boolean {
            return isServiceRunning(context, Music::class.java.name)
        }

        val PLAY_ACTION = "play"
        val PAUSE_ACTTION = "pause"
    }


    override fun onCreate(){
        super.onCreate()
        if (mediaPlayer == null) {
            // 播放音乐
            mediaPlayer = MediaPlayer.create(this, R.raw.backgroudmusic)
            mediaPlayer?.isLooping = true
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == Music.PLAY_ACTION) {
            mediaPlayer?.start()
        }
        if (action == Music.PAUSE_ACTTION) {
            mediaPlayer?.pause()
        }
        return Service.START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
    }
}