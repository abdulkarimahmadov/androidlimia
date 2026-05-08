package com.abdulkarimahmadov.androidlimia.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdulkarimahmadov.androidlimia.services.CallService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            context.startForegroundService(Intent(context, CallService::class.java))
        }
    }
}
