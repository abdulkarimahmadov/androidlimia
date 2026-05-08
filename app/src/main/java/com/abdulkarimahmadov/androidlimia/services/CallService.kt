package com.abdulkarimahmadov.androidlimia.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

class CallService : Service() {
    override fun onCreate() {
        super.onCreate()
        ensureNotificationChannel()
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("AndroidLimia")
            .setContentText("Call service active")
            .setSmallIcon(android.R.drawable.sym_call_incoming)
            .build()
        startForeground(101, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY
    override fun onBind(intent: Intent?): IBinder? = null

    private fun ensureNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Calls", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "limia_call_service"
    }
}
